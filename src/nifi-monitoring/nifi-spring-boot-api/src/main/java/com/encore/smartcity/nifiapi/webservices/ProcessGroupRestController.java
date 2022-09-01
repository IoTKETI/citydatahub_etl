package com.encore.smartcity.nifiapi.webservices;

import com.encore.smartcity.nifiapi.entities.flows.processgroup.ProcessGroup;
import com.encore.smartcity.nifiapi.services.ProcessGroupService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping(value = "/api/v1/process-groups")
@RestController
public class ProcessGroupRestController {

    private ProcessGroupService processGroupService;

    public ProcessGroupRestController(ProcessGroupService processGroupService) {
        this.processGroupService = processGroupService;
    }

    @GetMapping(value = "/{id}")
    public Map<String, Object> getAllComponents(@PathVariable("id") String id) throws IOException {
        Map<String, Object> response = new HashMap<>();

        ProcessGroup processGroup = this.processGroupService.getProcessGroupById(id).body();

        response.put("data", processGroup);
        response.put("status", true);
        response.put("message", "Get Process Group successfully!");

        return response;
    }

    public static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        String fileName = multipart.getOriginalFilename();
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        multipart.transferTo(convFile);
        return convFile;
    }

    public  String uploadTemplateFunction(String id, MultipartFile templateParam) throws  IOException{
        File file = ProcessGroupRestController.multipartToFile(templateParam);

        System.out.println(file.exists());

        MultipartBody.Part template = MultipartBody.Part.createFormData("template", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));

        System.out.println(template.body().contentLength());

        this.processGroupService.uploadTemplate(id, template);

        return templateParam.getOriginalFilename();

    }

    @PostMapping(value = "/{id}/upload/template")
    public ResponseEntity<Map<String, Object>> uploadTemplate(@PathVariable("id") String id, @RequestParam("template") MultipartFile[] templateParam) throws IOException {

        List<String> collect = Arrays.stream(templateParam)
                .map(file -> {
                    try {
                        return uploadTemplateFunction(id, file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());

        System.out.println(collect + " template is uploaded.");

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Upload Template successfully completed!");

        return new ResponseEntity<>(response, HttpStatus.OK);

//        return this.processGroupService.uploadTemplate(id, template).body();
    }

    /*@PostMapping(value = "/2/{id}")
    public String uploadTemplate2(@PathVariable("id") String id,@RequestParam("template") MultipartFile template) throws IOException {

        File file = ProcessGroupRestController.multipartToFile(template);

        System.out.println(file.exists());

//        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("template", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));

        System.out.println(filePart.body().contentLength());

        TemplateEntity templateEntity = this.processGroupService.uploadTemplate2(id, filePart).body();


        System.out.println(templateEntity);

        return template.getOriginalFilename();

//        return this.processGroupService.uploadTemplate(id, template).body();
    }
*/

    /*@PostMapping
    public String uploadFile45(@RequestParam("file") MultipartFile fileParam) throws IOException {

        File file = ProcessGroupRestController.multipartToFile(fileParam);

        System.out.println(file.exists());

//        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file3", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));

        System.out.println(filePart.body().contentLength());

        this.processGroupService.uploadFile45(filePart);


        return fileParam.getOriginalFilename();

//        return this.processGroupService.uploadTemplate(id, template).body();
    }*/

}

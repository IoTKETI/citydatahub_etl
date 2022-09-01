package com.encore.smartcity.nifiapi.webservices;

import com.encore.smartcity.externalconfig.JsonConfigProperties;
import com.encore.smartcity.utils.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.*;
import java.io.*;
import java.lang.management.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import oshi.util.Util;


@RequestMapping("/api/v1/hardware")
@RestController
public class HardwareRestController {

    private JsonConfigProperties jsonConfigProperties;

    public HardwareRestController(JsonConfigProperties jsonConfigProperties) {
        this.jsonConfigProperties = jsonConfigProperties;
    }

    @GetMapping("/cpu")
    public Map<String, Object> getCpuStatus() throws MalformedObjectNameException, ReflectionException, InstanceNotFoundException {
        Map<String, Object> response = new HashMap<>();

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
        AttributeList list = mbs.getAttributes(name, new String[]{"ProcessCpuLoad"});

        Attribute att = (Attribute) list.get(0);
        Double value = (Double) att.getValue();

        System.out.println((int) (value * 1000) / 10.0);

        response.put("cpu_usage", (int) (value * 1000) / 10.0 + " %");

        return response;
    }

    private static final Logger logger = LoggerFactory.getLogger(HardwareRestController.class);
    static List<String> oshi = new ArrayList<>();

    @GetMapping("/status")
    public Map<String, Object> getHardwareStatus() throws Exception {

        int nifiPID = getProcessIDByPortNumber(jsonConfigProperties.getNifiPort(), jsonConfigProperties.getRunningOS());

        Map<String, Object> response = new HashMap<>();

        SystemInfo si = new SystemInfo();

        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();

        logger.info("Checking Memory...");
        printMemory(hal.getMemory());

        logger.info("Checking CPU...");
        printCpu(hal.getProcessor());

        logger.info("Checking Processes...");
        printProcesses(os, hal.getMemory(), nifiPID);

//        logger.info("Checking Services...");
//        printServices(os);

        response.put("memory_available", oshi.get(0));
        response.put("total_memory_usage", oshi.get(1));
        response.put("cpu_load", oshi.get(2));
        response.put("total_cpu_usage", Double.parseDouble(oshi.get(3)));
        response.put("nifi_cpu_usage", Double.parseDouble(oshi.get(4)));
        response.put("nifi_memory_usage_percent", Double.parseDouble(oshi.get(5)));
        response.put("nifi_memory_usage", oshi.get(6));

        return response;
    }

    private static void printMemory(GlobalMemory memory) {
        oshi.add(0, String.valueOf(String.valueOf(FormatUtil.formatBytes(memory.getAvailable()))));
        oshi.add(1, String.valueOf(String.valueOf(FormatUtil.formatBytes(memory.getTotal() - memory.getAvailable()))));
    }

    private static void printCpu(CentralProcessor processor) {
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        oshi.add(2, String.valueOf(processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100));


        Util.sleep(1000);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long sys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long totalCpu = user + nice + sys + iowait + idle + irq + softirq + steal;

//        double cpu = ((user + nice + sys + iowait + irq + softirq + steal) * 100d) / totalCpu;
        double cpu = (100d * user / totalCpu + 100d * nice / totalCpu + 100d * sys / totalCpu +
                100d * iowait / totalCpu +  100d * irq / totalCpu +  100d * softirq / totalCpu +  100d * steal / totalCpu);
        oshi.add(3, String.format("%.2f", cpu));
    }

    private static void printProcesses(OperatingSystem os, GlobalMemory memory, int pid) {
        OSProcess p = os.getProcess(pid);
        // nifi cpu & memory

        // cpu as %
        oshi.add(4, String.format("%.2f", 100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime()));
        // nifi memory as %
        oshi.add(5, String.format("%.2f", 100d * p.getResidentSetSize() / memory.getTotal()));
        // nifi memory as MiB or GiB
        oshi.add(6, String.valueOf(FormatUtil.formatBytes(p.getResidentSetSize())));

    }

    /*
     * TODO: os value gonna be "MacOS" or "CentOS" or Ubuntu*/
    private static int getProcessIDByPortNumber(int portNumber, String os) throws Exception {

        String fullPath = System.getProperty("user.dir") + "/" + Const.nifiProcessId;
        PrintWriter writer = null;
        File file = new File(fullPath);
        if (!file.exists()) {
            writer = new PrintWriter(Const.nifiProcessId, "UTF-8");
            writer.close();
        }

        Path path = Paths.get(fullPath);
        boolean b = Files.readAllLines(path).isEmpty();

        if (!b) {
            String read = Files.readAllLines(path).get(0);
            System.out.println(read);
            return Integer.parseInt(read);
        }
        System.out.println("-------------------------------");
        Runtime rt = Runtime.getRuntime();
        Process proc;
        if (os.equals("MacOS"))
            proc = rt.exec("lsof -i :" + portNumber);
        else if (os.equals("CentOS") || os.equals("Ubuntu"))
            proc = rt.exec("sudo netstat -nlp | grep " + portNumber);
        else
            proc = rt.exec("sudo netstat -nlp | grep " + portNumber);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String s;
        StringBuilder sb = new StringBuilder();
        String sc;
        while ((s = stdInput.readLine()) != null) {
            // Start get process ID on MacOS
            /*TODO: Sometimes error because of substring*/
            if (os.equals("MacOS")) {
                if (s.contains("java")) {
                    sb.append(s);
                    sc = sb.toString();
                    System.out.println(sc);
                    try {
                        System.out.println(sc.substring(10, 15).trim());

                        byte[] strToBytes = sc.substring(10, 15).trim().getBytes();
                        Files.write(path, strToBytes);

                        return Integer.parseInt(sc.substring(10, 15).trim());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    System.out.println(sc.substring(8, 13).trim());

                    byte[] strToBytes = sc.substring(8, 13).trim().getBytes();
                    Files.write(path, strToBytes);

                    return Integer.parseInt(sc.substring(8, 13).trim());
                } else if (s.contains("com.docke")) {
                    sb.append(s);
                    sc = sb.toString();
                    System.out.println(sc);
                    System.out.println(sc.substring(10, 15).trim());

                    int m = sc.lastIndexOf("java");
                    System.out.println("Okokok: " + m);

                    byte[] strToBytes = sc.substring(10, 15).trim().getBytes();
                    Files.write(path, strToBytes);

                    return Integer.parseInt(sc.substring(10, 15).trim());
                }
            }
            //End get process ID on MacOS

            //Start get process ID on Linux(CentOS7)
            else if (os.equals("CentOS") || os.equals("Ubuntu")) {
                if (s.contains(String.valueOf(portNumber))) {
                    sb.append(s);
                    String process = sb.toString().trim();
                    int index = process.lastIndexOf(" ") + 1;
                    int to = process.indexOf("/");
                    sc = process.substring(index, to);
                    System.out.println(sc);

                    byte[] strToBytes = sc.getBytes();
                    Files.write(path, strToBytes);

                    return Integer.parseInt(sc);
                }
            }
            //End get process ID on Linux(CentOS7)
        }
        return 0;
    }

}

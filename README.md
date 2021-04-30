ETL templates of City Data Hub
=============

### KAFKA
* template-1 : This is a template that consumes message from KAFKA. There are two main type of Consumekafka processors that can be applied. User ID and Password can be divided into cases with and without.
* template-2 : This is a template that consumes a compressed message from KAFKA.
* template-3 : This is a template for publishing a message to KAFKA.
* template-4 : It is a template that compresses a message and publishes into KAFKA.
* * *
### JSON file
* template-5 : It is a template that extracts the value of a specific key from the JSON file. Since the value needs to be extracted, the destination is set as flowfile-content.
* template-6 : This is a template that extracts the value of a specific key from the JSON file and adds it as an attribute of the flowfile. To extract only value, flowfile-content is used, but to add to attribute, set destination as flowfile-attribute.
* template-7 : It is a template that extracts the value of a specific key from the JSON file, adds it to the flowfile-attribute, and routes it based on this.
* * *
### Routing
* template-8 : This is a template using EvaluateJsonPath and RouteOnAttribute processor for processing complex route conditions.
* template-9 : It is a template used to filter the case that the value is empty because a specific key does not exist in the JSON file. Add JSON file key to user defined property, check whether the property exists, and route. Check it using the isEmpty() function.
* * *
### Validation & Filtering
* template-10 : This is a template for validating values. Filter conditions are added to check format and validation, and the contents of filter conditions are writtedn using regular expressions.
* template-11 : This is a template for validating a range of values. The validity of the range of latitude and logitude is checked.
* template-12 : This is a template for merging JSON files. Each JSON file is loaded by separating it with a new line. Set (?s)(^.*$) as search value in each JSON file, set replacement value to new line.
* template-13 : This is a template that extracts only a specific part from a JSON file and merges only its contents. Extract and transform using the JoltTransformJSON processor.
* template-14 : After extracting only the content from JSON file and converting the record format to CSV style, merge records using the merge record processor.
* * *
### Read and Write files in HDFS
* template-15 : It is a template that can fetch the file from HDFS. 
* template-16 : This is a template for writing files to HDFS.
* * *
### Scheduling Read and Write files in HDFS
* template-17 : This is a template for reading a file from HDFS at a specifed time. Set the scheduling strategy in the SCHEDULING tab of the Configuration Processor. There are Timer Driven and CRON Driven options.
* * *
### Read and Write parquet files in HDFS
* template-18 : This is a template that reads a parquet format file form HDFS using the FetchParquet processor. Parquet file contents can be read based on this schema.
* * *
### Record Reader
* template-19 : This is a template that writes JSON file to HDFS as parquet format files. Use JSONTreeReader as the record reader and set the compression type to snappy.
* template-20 : This is a template of retrying logic when the job is executed but fails.

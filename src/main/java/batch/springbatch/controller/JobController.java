package batch.springbatch.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    private final static String tempStorage = "C:\\Users\\Sj\\Desktop\\temp";


    @PostMapping("/importCustomers")
    public void importCsvToDBJob(@RequestParam("file")MultipartFile multipartFile) {
        try {
            String originalFileName=multipartFile.getOriginalFilename();
            File fileToImport=new File(tempStorage+originalFileName);
            multipartFile.transferTo(fileToImport);

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("fullPathName",tempStorage+originalFileName)
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
            JobExecution execution = jobLauncher.run(job, jobParameters);

//            if (execution.getExitStatus().equals(ExitStatus.COMPLETED))
//            {
//                // after delete file from temp storage
//
//                Files.deleteIfExists(Paths.get(tempStorage+originalFileName));
//            }

        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
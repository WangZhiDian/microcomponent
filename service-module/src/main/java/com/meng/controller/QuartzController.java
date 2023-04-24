package com.meng.controller;

import com.meng.bean.ApiResult;
import com.meng.job.QuartzJob;
import com.meng.quartz.tool.QuartzJobTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述总结：
 */
@Slf4j
@RestController
public class QuartzController {

    @Autowired
    QuartzJobTools quartzJobTools;

    @GetMapping(value = "/quartz/create")
    public ApiResult<Object> deal(@RequestParam("jobname") String jobname,
                                  @RequestParam("jobgroup") String jobgroup,
                                  @RequestParam("triggername") String triggername,
                                  @RequestParam("triggergroup") String triggergroup,
                                  @RequestParam("cron") String cron,
                                  @RequestParam("info") String info) throws InterruptedException {

        Map<String, String> map = new HashMap<>();
        map.put("info", info);
        quartzJobTools.addJob(QuartzJob.class, jobname, jobgroup, triggername, triggergroup, cron, map);
        return ApiResult.success("suc");
    }

    @GetMapping(value = "/quartz/pause")
    public ApiResult<Object> deal2(@RequestParam("jobname") String jobname,
                                  @RequestParam("jobgroup") String jobgroup) {

        quartzJobTools.pauseJob(jobname, jobgroup);
        return ApiResult.success("suc");
    }

/*    @GetMapping(value = "/quartz/stop")
    public ApiResult<Object> deal3() {
        quartzJobTools.shutdownJobs();
        return ApiResult.success("suc");
    }*/

    @GetMapping(value = "/quartz/resume")
    public ApiResult<Object> deal4(@RequestParam("jobname") String jobname,
                                  @RequestParam("jobgroup") String jobgroup) {
        quartzJobTools.resumeJob(jobname, jobgroup);
        return ApiResult.success("suc");
    }

    @GetMapping(value = "/quartz/delete")
    public ApiResult<Object> deal5(@RequestParam("jobname") String jobname,
                                  @RequestParam("jobgroup") String jobgroup,
                                  @RequestParam("triggername") String triggername,
                                  @RequestParam("triggergroup") String triggergroup) {
        quartzJobTools.removeJob(jobname, jobgroup, triggername, triggergroup);
        return ApiResult.success("suc");
    }


}

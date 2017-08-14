package spider.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017/8/14.
 */
public class TestJob implements Job {
    Logger logger = LoggerFactory.getLogger(Job.class);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("excute test job!");
    }
}

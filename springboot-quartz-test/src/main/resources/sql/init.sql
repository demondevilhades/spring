
DROP TABLE IF EXISTS `task_config`;
CREATE TABLE `task_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(255) NOT NULL,
  `job_group` varchar(255) NOT NULL,
  `trigger_name` varchar(255) NOT NULL,
  `trigger_group` varchar(255) NOT NULL,
  `job_data` varchar(255) DEFAULT NULL,
  `cron_expression` varchar(32) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `start_time` datetime(0) DEFAULT NULL,
  `end_time` datetime(0) DEFAULT NULL,
  `start_now` tinyint(1) UNSIGNED NOT NULL DEFAULT 0,
  `priority` tinyint(1) UNSIGNED NOT NULL DEFAULT 0,
  `misfire_handling_instruction` tinyint(1) UNSIGNED NOT NULL DEFAULT 0,
  `bean_class` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `createtime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT 'auto insert',
  `updatetime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'auto update',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `quartz`.`task_config`(`job_name`, `job_group`, `trigger_name`, `trigger_group`, `job_data`, `cron_expression`, `description`, `bean_class`, `status`) VALUES ('testJob', 'testGroup', 'testJobTrigger', 'testGroup', 'testJobData', '*/10 * * * * ?', 'testJobDesc', 'test.springboot.job.TestJob', 1);
INSERT INTO `quartz`.`task_config`(`job_name`, `job_group`, `trigger_name`, `trigger_group`, `job_data`, `cron_expression`, `description`, `bean_class`, `status`) VALUES ('testInvokeJob', 'testGroup', 'testInfokeJobTrigger', 'testGroup', '{\"className\":\"test.springboot.job.TestInvokeMethod\",\"methodName\":\"test\",\"arg\":\"testArgs\"}', '*/15 * * * * ?', 'testInvokeJobDesc', 'test.springboot.job.InvokeMethodJob', 1);


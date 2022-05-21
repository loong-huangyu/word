

import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.lang.Boolean

@Configuration
@ConfigurationProperties(prefix = "thread.pool")
class ThreadPoolConfig {

    private var corePoolSize = CORE_POOL_SIZE
    var maximumPoolSize = CORE_POOL_SIZE shl 1
    private var keepAliveTime = KEEP_ALIVE_TIME
    private var queueSize = QUEUE_SIZE
    var awaitTerminationSeconds = AWAIT_TERMINATION_SECONDS

    @Bean("taskExecutor")
    fun taskExecutor(): TaskExecutor {
        log.debug("获取到当前cpu个数 '{}' .", getCorePoolSize())
        val taskExecutor = ThreadPoolTaskExecutor()
        taskExecutor.setWaitForTasksToCompleteOnShutdown(Boolean.TRUE)
        taskExecutor.setAwaitTerminationSeconds(awaitTerminationSeconds)
        taskExecutor.corePoolSize = corePoolSize
        taskExecutor.maxPoolSize = maximumPoolSize
        taskExecutor.keepAliveSeconds = keepAliveTime
        taskExecutor.setQueueCapacity(queueSize)
        taskExecutor.setThreadNamePrefix("async-")
        return taskExecutor
    }

    fun setQueueSize(queueSize: Int) {
        this.queueSize = queueSize
    }

    fun setCorePoolSize(corePoolSize: Int) {
        this.corePoolSize = corePoolSize
    }

    fun setKeepAliveTime(keepAliveTime: Int) {
        this.keepAliveTime = keepAliveTime
    }

    companion object {

        private val log = LoggerFactory.getLogger(ThreadPoolConfig::class.java)
        private const val QUEUE_SIZE = 1000
        private val CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors()
        private const val KEEP_ALIVE_TIME = 300
        private const val AWAIT_TERMINATION_SECONDS = 60
        fun getQueueSize(): Int {
            return QUEUE_SIZE
        }

        fun getCorePoolSize(): Int {
            return CORE_POOL_SIZE
        }

        fun getKeepAliveTime(): Int {
            return KEEP_ALIVE_TIME
        }
    }
}
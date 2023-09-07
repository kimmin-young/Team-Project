package com.team.prj.user;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer{

	@Override
	@Bean(name = "mailExecutor")
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(3);		// 실행 대기중인 Thread의 갯수
											// 아무 작업이 없어도 갯수만큼 스레드가 생성됨
		
		// 현재 3개중 3개 사용중 회원가입, 비밀번호 찾기, 네이버 로그인 임시 비밀번호
		// 더 사용하려면 풀사이즈를 늘려야 함
		
		executor.setMaxPoolSize(5);			// 동시에 동작하는 최대 Thread의 갯수
											// QueueCapacity까지 차는 경우 maxPoolSize만큼 넓혀감
		
		executor.setQueueCapacity(10);		// CorePool의 크기를 넘어서면 저장하는 큐의 최대 용량
		
		executor.setThreadNamePrefix("Async MailExecutor"); // 스레드에 사용할 이름
		executor.initialize();
		
		return executor;
	}
	
	// 스레드를 다중 스레드로 활용한다면
	// bean name에 들어가는 이름을 다르게 해서 만들어야 함

}

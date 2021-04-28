package fr.commerces.task;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

class TaskCompleted {
  // ...
}


@ApplicationScoped
class ComplicatedService {

   @Inject
   Event<TaskCompleted> event; 

   void doSomething() {
      // ...
      event.fire(new TaskCompleted()); 
   }

}

@Slf4j
@ApplicationScoped
class Logger {

   void onTaskCompleted(@Observes TaskCompleted task) { 
      // ...log the task
	   log.info("Task completed !");
   }

}
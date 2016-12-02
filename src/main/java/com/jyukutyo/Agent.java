package com.jyukutyo;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;

public class Agent {

  public static void agentmain(String agentArgs, Instrumentation inst) throws Exception {
    System.out.println("agentmain is called");

    Map<String, Class> classMap = Arrays.stream(inst.getAllLoadedClasses()).collect(Collectors.toMap(c -> c.getName(), c -> c, (c1, c2) -> c1));
    String target = "com.jyukutyo.TestController";
    ClassLoader classLoader = classMap.get(target).getClassLoader();

    ByteBuddy byteBuddy = new ByteBuddy();
    byteBuddy
            .redefine(TypePool.Default.of(classLoader).describe(target).resolve(),
                    ClassFileLocator.ForClassLoader.of(classLoader))
            .method(ElementMatchers.named("index"))
            .intercept(FixedValue.value("transformed"))
            .make()
            .load(classLoader, ClassReloadingStrategy.of(inst));

    new AgentBuilder.Default()
            .with(byteBuddy)
            .installOn(inst);
  }

}

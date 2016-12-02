package com.jyukutyo;

import java.io.IOException;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

public class Main {
    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        VirtualMachine attach = VirtualMachine.attach("36468");
        attach.loadAgent("/Users/jyukutyo/code/bytecode-example2/target/bytecode-example-1.0-SNAPSHOT-jar-with-dependencies.jar");
        attach.detach();
    }
}

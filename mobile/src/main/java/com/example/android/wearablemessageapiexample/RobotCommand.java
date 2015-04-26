package com.example.android.wearablemessageapiexample;


import org.codehaus.plexus.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RobotCommand {

    public enum Command {
        Greet2,
        Right_Jab,
        Right_Hook,
        Left_Jab,
        Left_Hook,
        Left_Uppercut,
        Right_Hand_Wave,
        Left_Hand_Wave,
        Back_Roll,
        Left_Attack1,
        Right_Attack1,
        Left_Attack2,
        Right_Attack2,
        Front_Attack,
        Left_Kick,
        Right_Kick,
        Left_Side_Kick,
        Horse_Dance,
        NoCommand
    }

    private static List<String> buttonMovements = createButtonMovements();

    private static List<String> createButtonMovements() {
        List<String> movementList = new  ArrayList<String>();
        movementList.add("Left_Turn(2)");
        movementList.add("Right_Turn(2)");
        movementList.add("Advance");
        return movementList;
    }

    private static  Map<String, Command> referenceMap = createReferenceMap();

    private List<Command> commands = new ArrayList<Command>();

    private String buttonCommand = "";

    public RobotCommand(String voiceCommand){

        if (buttonMovements.contains(voiceCommand))
        {
            buttonCommand = voiceCommand;
        }
        String[] voiceCommandArray = StringUtils.split(voiceCommand, " ");
        Command command;

        for (String word : voiceCommandArray) {
            command = matchStringWithMovement(word);
            if(command != Command.NoCommand){
                commands.add(command);
            }
        }
    }

    private static Map<String,Command> createReferenceMap() {
        Map<String, Command> newReferenceMap = new HashMap<String, Command>();

        newReferenceMap.put("golpe;trompada;puño;mano;pegar;golpear", Command.Front_Attack);
        newReferenceMap.put("patada,pie,patear,pata,pierna", Command.Left_Kick);
        return newReferenceMap;
    }

    private Command matchStringWithMovement(String word){

        for (String key : referenceMap.keySet()) {
            if(key.contains(word)){
                return referenceMap.get(key);
            }
        }
        for (Command command : Command.values()) {
            if(command.name().equals(word)){
                return command;
            }
        }
        return Command.NoCommand;
    }

    public String getCommand() {
        return buildCommand();
    }

    private String buildCommand() {
        if (!buttonCommand.isEmpty()){
            return buttonCommand;
        }
        String completeCommand = "";
        for(Command command: commands){
            completeCommand = completeCommand.concat(command.name() + "->");
        }
        if (!completeCommand.isEmpty()) {
            completeCommand = completeCommand.substring(0, completeCommand.length() - 2);
        }
        return  completeCommand;
    }


}

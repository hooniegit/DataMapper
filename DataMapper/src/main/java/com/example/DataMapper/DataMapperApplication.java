package com.example.DataMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.DataMapper.Class.Refactor;
import com.example.DataMapper.Class.Reference;
import com.example.DataMapper.Class.Target;

public class DataMapperApplication {
	public static void main(String[] args) {
		
		// [Create] Sample Target Data
        List<Target> targetObjects = Arrays.asList(
            getTarget("SRC001", "SRC001.T0001", "1000", "2024:08:11 00:00:00"),
            getTarget("SRC001", "SRC001.T0002", "1000", "2024:08:11 00:00:05"),
            getTarget("SRC001", "SRC001.T0003", "1000", "2024:08:11 00:00:10"),
            getTarget("SRC002", "SRC002.T0001", "1000", "2024:08:11 00:00:00"),
            getTarget("SRC002", "SRC002.T0002", "1000", "2024:08:11 00:00:05"),
            getTarget("SRC002", "SRC002.T0003", "1000", "2024:08:11 00:00:10"),
            getTarget("SRC003", "SRC003.T0001", "1000", "2024:08:11 00:00:00"),
            getTarget("SRC003", "SRC003.T0002", "1000", "2024:08:11 00:00:05"),
            getTarget("SRC003", "SRC003.T0003", "1000", "2024:08:11 00:00:10")
        );

        // [Create] Sample Reference Table
        List<Reference> referenceObjects = Arrays.asList(
            getReference("SRC001.T0001", 1, 1),
            getReference("SRC001.T0002", 1, 2),
            getReference("SRC001.T0003", 1, 3),
            getReference("SRC002.T0001", 2, 2001),
            getReference("SRC002.T0002", 2, 2002),
            getReference("SRC002.T0003", 2, 2003),
            getReference("SRC003.T0001", 3, 4001),
            getReference("SRC003.T0002", 3, 4002),
            getReference("SRC003.T0003", 3, 4003)
        );

        // [Map] ID <-> VALUE (at Target)
        Map<String, String> idToValueMap = targetObjects.stream()
            .collect(Collectors.toMap(Target::getID, Target::getVALUE));
        
        // [Map] ID <-> TIMESTAMP (at Target)
        Map<String, String> idToTimeStampMap = targetObjects.stream()
                .collect(Collectors.toMap(Target::getID, Target::getTIMESTAMP));

        // [Refactor] Create Refactor Data / Using Map
        List<Refactor> refactorObjects = referenceObjects.stream()
            .filter(Reference -> Reference.getGROUP() == 1) 
            .map(Reference -> new Refactor(Reference.getID(), idToValueMap.get(Reference.getID()), Reference.getINDEX(), idToTimeStampMap.get(Reference.getID())))
            .collect(Collectors.toList());

        // [Test] Print
        refactorObjects.forEach(System.out::println);
        
        // [Refactor] Create Refactor Data / Stand Alone
        List<Refactor> refactorObjects2 = referenceObjects.stream()
            .filter(reference -> reference.getGROUP() == 1)
            .map(reference -> {
                Target matchingTarget = targetObjects.stream()
                    .filter(target -> target.getID().equals(reference.getID()))
                    .findFirst()
                    .orElse(null);
                if (matchingTarget != null) {
                    return new Refactor(
                        reference.getID(),
                        matchingTarget.getVALUE(),
                        reference.getINDEX(),
                        matchingTarget.getTIMESTAMP()
                    );
                } else {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        // [Test] Print
        refactorObjects2.forEach(System.out::println);
        
	}
	
	private static Target getTarget(String SOURCE, String ID, String VALUE, String TIMESTAMP) {
		Target target = new Target();
		target.setSOURCE(SOURCE);
		target.setID(ID);
		target.setVALUE(VALUE);
		target.setTIMESTAMP(TIMESTAMP);
		
		return target;
	}
	
	private static Reference getReference(String ID, int GROUP, int INDEX) {
		Reference reference = new Reference();
		reference.setID(ID);
		reference.setGROUP(GROUP);
		reference.setINDEX(INDEX);
		
		return reference;
	}
}

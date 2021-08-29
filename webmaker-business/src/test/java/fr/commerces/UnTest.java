package fr.commerces;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class UnTest {

	
	@Test
	public void testUn() {
		String test = "RossiniEnergy,839265873,info@rossinienergy.com,RossiniEnergy,info@rossinienergy.com,374090105,RossiniEnergy,FRROSE84,,moreaux_epinoy,Parking privé à usage public,\"1 rue de la Mairie, 62860 Epinoy, France\",62298,\"[ 50.2304620,3.1610000]\",2,FRROSE841,,22,TRUE,TRUE,FALSE,FALSE,FALSE,FALSE,TRUE,TRUE,FALSE,,Accès libre,FALSE,24/7,Accessible mais non réservé PMR,3m,FALSE,Indirect,1332127312566,2021-05-07,,2021-08-10,2021-08-11T16:17:53.443000,71251221-21d8-4d7e-90d6-0fde5c1af96b";
		
		Collection<List<String>> collection = splitCSVFile(test, 41, true);
		
		
		assertNotNull(collection);
	}
	
	
	public static Collection<List<String>> splitCSVFile(String content, int nbCellByLine, boolean deleteQuoted)
	{
		if (content == null) 
		{
			return Collections.emptyList();
		}
		String regex = "(?:,|\\n|^)(\"(?:(?:\"\")*[^\"]*)*\"|[^\",\\n]*|(?:\\n|$))";
		Matcher matcher = Pattern.compile(regex).matcher(content);
		List<String> result = new ArrayList<>();
		while (matcher.find()) 
		{
			result.add(deleteQuoted ? matcher.group(1).replace("\"", "") : matcher.group(1));
		}
		final AtomicInteger counter = new AtomicInteger();
		return result.stream()
			    .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / nbCellByLine))
			    .values();
	}

	
}

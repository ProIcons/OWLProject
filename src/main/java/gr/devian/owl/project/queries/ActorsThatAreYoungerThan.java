package gr.devian.owl.project.queries;

import gr.devian.owl.project.models.AgeOperator;
import gr.devian.owl.project.util.QueryUtils;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class ActorsThatAreYoungerThan implements BaseQuery {
    @Override
    public ResultSet executeQuery(final Object[] arguments, final Model model) {
        final int age = (int) arguments[0];
        final AgeOperator ageOperator = (AgeOperator) arguments[1];

        System.out.printf("""
                        
                Actors that are %s than %d years old.
                %n""", ageOperator == AgeOperator.YOUNGER ? "younger" : "older", age);

        final String resultingDateTime = LocalDateTime
                .now()
                .minusYears(age)
                .toLocalDate()
                .atTime(0, 0, 0)
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        final String dateTimeNow = LocalDateTime
                .now()
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        final String queryString = """
                PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
                PREFIX ont: <http://devian.gr/movieproductionontology#>
                            
                SELECT DISTINCT ?actor (year(?dateTimeNow)-year(?birthDay) as ?age)
                WHERE {
                  ?actor ont:wasLeadInMovie|ont:wasExtraInMovie|ont:wasSupportInMovie ?movie.
                  ?actor ont:hasBirthday ?birthDay.
                  BIND ("%s"^^xsd:dateTime AS ?targetDateTime)
                  BIND ("%s"^^xsd:dateTime AS ?dateTimeNow)
                  FILTER (?birthDay %s ?targetDateTime)
                }
                """.formatted(resultingDateTime, dateTimeNow, ageOperator == AgeOperator.OLDER ? "<" : ">");

        return QueryUtils.query(model, queryString);
    }

    @Override
    public Object[][] executionCases() {
        return new Object[][]{
                new Object[]{
                        35,
                        AgeOperator.YOUNGER
                },
                new Object[]{
                        40,
                        AgeOperator.OLDER
                }
        };
    }
}

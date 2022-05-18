package gr.devian.owl.project.queries;

import gr.devian.owl.project.models.Role;
import gr.devian.owl.project.util.QueryUtils;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PeopleThatHaveMoreThanOneRolesInMovie implements BaseQuery {
    @Override
    public ResultSet executeQuery(final Object[] arguments, final Model model) {
        final var roles = Arrays.stream(arguments).map(argument -> (Role) argument).toList();

        System.out.printf("""
                        
                People that are %s in the same Movie
                %n""", roles.stream().map(Role::toString).collect(Collectors.joining(" & ")));

        final StringBuilder roleConstraints = new StringBuilder();
        for (final Role role : roles) {
            roleConstraints.append(" ?person ");
            roleConstraints.append(role.getOntologyRelationship());
            roleConstraints.append(" ?movie. \n");
        }

        final String queryString = """
                PREFIX ont: <http://devian.gr/movieproductionontology#>
                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                            
                SELECT
                    ?person (CONCAT(?firstName, " ", ?lastName) AS ?personName)
                    ?movie
                    ?movieName
                    (group_concat(?property;separator=" | ") as ?roles)
                WHERE {
                  %s
                  ?person ?property ?movie.
                  ?property rdfs:subPropertyOf ont:hasParticipatedInMovie.
                  ?person ont:hasFirstname ?firstName.
                  ?person ont:hasLastname ?lastName.
                  ?movie ont:hasName ?movieName.
                  FILTER (?property != ont:hasParticipatedInMovie)
                }
                GROUP BY ?person ?movie ?firstName ?lastName ?movieName ?firstName ?lastName
                """.formatted(roleConstraints);

        return QueryUtils.query(model, queryString);
    }

    @Override
    public Object[][] executionCases() {
        return new Object[][]{new Object[]{Role.DIRECTOR, Role.SCREENWRITER}};
    }
}

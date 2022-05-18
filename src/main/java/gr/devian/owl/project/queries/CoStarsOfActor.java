package gr.devian.owl.project.queries;

import gr.devian.owl.project.util.QueryUtils;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

public class CoStarsOfActor implements BaseQuery {
    @Override
    public ResultSet executeQuery(final Object[] arguments, final Model model) {
        System.out.printf("""
                        
                Co-Stars of %s %s per movie
                %n""", arguments);

        final String queryString = """
                PREFIX ont: <http://devian.gr/movieproductionontology#>
                            
                SELECT ?actor ?leadActor ?movie
                WHERE {
                  ?actor ont:hasFirstname "%s".
                  ?actor ont:hasLastname "%s".
                  ?actor ont:wasLeadInMovie ?movie.
                  ?movie ont:leadBy ?leadActor.
                  FILTER (?leadActor != ?actor)
                }
                """.formatted(arguments);

        return QueryUtils.query(model, queryString);
    }

    @Override
    public Object[][] executionCases() {
        return new Object[][]{
                new Object[]{
                        "Alicia",
                        "Vikander"
                },
                new Object[]{
                        "Margot",
                        "Robbie"
                }
        };
    }
}

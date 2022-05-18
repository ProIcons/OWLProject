package gr.devian.owl.project.queries;

import gr.devian.owl.project.util.QueryUtils;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

public class GetMovieCreditsWithoutExtras implements BaseQuery {
    @Override
    public ResultSet executeQuery(final Object[] arguments, final Model model) {
        System.out.printf("""
                                
                Movie "%s" Credits without Extras
                %n""", arguments[0]);

        final String queryString = """
                PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                PREFIX ont: <http://devian.gr/movieproductionontology#>
                            
                SELECT DISTINCT
                    ?movie
                    ?credit
                WHERE {
                  ?movie ont:hasName "%s".
                  ?credit ont:hasParticipatedInMovie ?movie.
                  ?credit rdf:type ont:Person.
                  MINUS {
                    ?credit ont:wasExtraInMovie ?movie
                  }
                }
                """.formatted(arguments[0]);

        return QueryUtils.query(model, queryString);
    }

    @Override
    public Object[][] executionCases() {
        return new Object[][]{new Object[]{"Ex Machina"}, new Object[]{"Tomb Raider"}};
    }
}

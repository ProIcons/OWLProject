package gr.devian.owl.project.queries;

import gr.devian.owl.project.util.QueryUtils;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

public class MostUsedGenreByActor implements BaseQuery {
    @Override
    public ResultSet executeQuery(final Object[] arguments, final Model model) {
        System.out.println("""
                                
                Genre and the actors used this genre
                """);

        final String queryString = """
                PREFIX ont: <http://devian.gr/movieproductionontology#>
                SELECT DISTINCT
                    ?genre
                    ?credit
                    (COUNT(?credit) as ?creditCount)
                WHERE {
                  ?movie ont:hasGenre ?genre.
                  ?credit ont:hasParticipatedInMovie ?movie.
                  ?credit ?property ?movie.
                  FILTER (?property IN (ont:wasLeadInMovie,ont:wasExtraInMovie,ont:wasSupportInMovie))
                }
                GROUP BY ?genre ?credit
                ORDER BY DESC(?creditCount)
                """;

        return QueryUtils.query(model, queryString);
    }

    @Override
    public Object[][] executionCases() {
        return new Object[][]{};
    }
}

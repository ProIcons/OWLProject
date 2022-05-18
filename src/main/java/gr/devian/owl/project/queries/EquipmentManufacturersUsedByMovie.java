package gr.devian.owl.project.queries;

import gr.devian.owl.project.util.QueryUtils;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

public class EquipmentManufacturersUsedByMovie implements BaseQuery {
    @Override
    public ResultSet executeQuery(final Object[] arguments, final Model model) {
        System.out.println("""
                                
                Equipment Manufacturers Used by Movies
                """);

        final String queryString = """
                PREFIX ont: <http://devian.gr/movieproductionontology#>
                            
                SELECT DISTINCT
                    ?movie
                    (COUNT(DISTINCT ?manufacturer) as ?manufacturersUsed)
                    (GROUP_CONCAT(DISTINCT ?manufacturerName;separator=" | ") as ?manufacturedNames)
                WHERE {
                  ?movie ont:hasName ?movieName.
                  ?equipment ont:equipmentUsedBy ?movie.
                  ?equipment ont:manufacturedBy ?manufacturer.
                  ?manufacturer ont:hasName ?manufacturerName.
                }
                GROUP BY ?type ?movie
                """;

        return QueryUtils.query(model, queryString);
    }

    @Override
    public Object[][] executionCases() {
        return new Object[][]{};
    }
}

package gr.devian.owl.project.queries;

import gr.devian.owl.project.util.QueryUtils;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

public class EquipmentUsedInMovie implements BaseQuery {
    @Override
    public ResultSet executeQuery(final Object[] arguments, final Model model) {
        System.out.printf("""
                                
                Equipment used by %s
                %n""", arguments[0]);

        final String queryString = """
                PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                PREFIX ont: <http://devian.gr/movieproductionontology#>
                            
                SELECT DISTINCT
                    ?type
                    (COUNT(?equipment) as ?equipmentCount)
                    (GROUP_CONCAT(?equipmentName;separator=" | ") as ?equipmentNames)
                WHERE {
                  ?movie ont:hasName "%s".
                  ?equipment ont:equipmentUsedBy ?movie.
                  ?equipment rdf:type ?type.
                  ?equipment ont:hasName ?equipmentName.
                  ?type rdfs:subClassOf* ont:Equipment.
                  FILTER (?type != ont:Equipment)
                }
                GROUP BY ?type
                """.formatted(arguments[0]);

        return QueryUtils.query(model, queryString);
    }

    @Override
    public Object[][] executionCases() {
        return new Object[][]{new Object[]{"Ex Machina"}, new Object[]{"Tomb Raider"}};
    }
}

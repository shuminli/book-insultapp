package org.openshift;

/**
 * Created by shumin on 7/6/16.
 */

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.HashMap;
import java.util.Map;

@RequestScoped
@Path("/insult")
public class InsultResource {

    @GET()
    @Produces("application/json")
    public Map<String, String> getInsult() {
        final Map<String, String> theInsult = new HashMap<String, String>();
        theInsult.put("insult", new InsultGenerator().generateInsult());

        return theInsult;
    }
}

package kz.edu.nu.cs.exercise;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("/items")
public class PagingService {

    private List<String> list = new CopyOnWriteArrayList<String>();

    public PagingService() {
        for (int i = 0; i < 100; i++) {
            list.add("Entry " + String.valueOf(i));
        }
    }

    @GET
    public Response getMyList(@QueryParam("page") int page) {
        Gson gson = new Gson();
        String json;
        
        PagedHelper p = new PagedHelper();
        
        if (page - 1 < 0) {
        	p.setPrev("-1");
        }
        else if (page + 1 > (list.size()/10 - 1)) {
        	p.setNext("100");
        }
        else {
        	p.setPrev(String.valueOf(page - 1)); 
            p.setNext(String.valueOf(page + 1));
        }
             
        p.setList(list.subList(10*page, 10 + (10*page)));
        
        json = gson.toJson(p, PagedHelper.class);
        
        return Response.ok(json).build();
    }
}

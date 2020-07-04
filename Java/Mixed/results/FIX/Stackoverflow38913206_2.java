class Stackoverflow38913206_2{
public class Credentials implements Serializable {

    private String user;
    private String pass;
    private String url;

    // Getters and setters omitted
}

@POST
    @Consumer(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String foo(Credentials credentials) {
       // ...
    }
}
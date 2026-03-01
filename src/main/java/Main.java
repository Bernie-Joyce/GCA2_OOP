import com.fasterxml.jackson.databind.ObjectMapper;

private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

void main() throws Exception {

    String url = "jdbc:mysql://localhost:8889/CatnOwner?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    String user = "root";
    String pass = "root";

    CatDao dao = new JdbcCatDao(url, user, pass);
    CatService service = new CatService(dao);

//    for(Cat cat : service.filterGender(Gender.MALE)){
//        IO.println(" - " + cat);
//    }


    List<Cat> cats = service.listCats();
    Cat cat = cats.getFirst();

    String json = service.catToJSON(cat);
    IO.println(json);

    Cat cat1 = service.catFromJSON(json);

    IO.println(cat1.getName());

    String jsonList = service.catListToJSON(cats);

    IO.println(jsonList);

//
//    IO.println("All cats:");
//    for (Cat cat : service.listCats())
//        IO.println(" - " + cat);
//

}

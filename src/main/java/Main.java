import domain.*;
import service.CatService;
import service.OwnerService;
import service.ServiceFactory;

void main() throws Exception {
    ServiceFactory serviceFactory = new ServiceFactory();
    CatService catService =  serviceFactory.createCatService();
    OwnerService ownerService = serviceFactory.createOwnerService();

    System.out.println("\n#####################################");
    System.out.println("           Cats:");
    System.out.println("#####################################\n");

    List<Cat> cats = catService.listCats();
    Cat cat = cats.getFirst();

    String json = catService.catToJSON(cat);
    IO.println(json);

    Cat cat1 = catService.catFromJSON(json);

    IO.println(cat1.getName());

    String jsonList = catService.catListToJSON(cats);

    IO.println(jsonList);

    System.out.println("\n#####################################");
    System.out.println("           Owners:");
    System.out.println("#####################################\n");



    List<Owner> owners = ownerService.listOwners();
    Owner owner = owners.getFirst();

    String ownerJson = ownerService.ownerToJson(owner);
    IO.println(ownerJson);

    Owner owner1 = ownerService.ownerFromJson(ownerJson);

    IO.println(owner1.getFirstName());

    String ownerJsonList = ownerService.ownerListToJson(owners);

    IO.println(ownerJsonList);

    System.out.println(ownerService.getOwner(1));
    ownerService.updateOwner(1, new Owner("Jeff", "Doe", 432, "Somewhere", "+35312345678", "fake@email.com"));
    System.out.println(ownerService.getOwner(1));

}
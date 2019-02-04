package bdi.azd.gedi.config;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.github.javafaker.Faker;
import bdi.azd.gedi.data.model.Group;
import bdi.azd.gedi.data.model.Protocollo;
import bdi.azd.gedi.data.model.StatoProtocollo;
import bdi.azd.gedi.data.model.User;
import bdi.azd.gedi.data.repository.GroupRepository;
import bdi.azd.gedi.data.repository.ProtocolloRepository;
import bdi.azd.gedi.data.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InitializeData {

  @Autowired GroupRepository groupRepo;

  @Autowired UserRepository userRepo;

  @Autowired ProtocolloRepository protocolloRepo;

  private Faker faker = Faker.instance(Locale.ITALIAN, new Random(1));

  @EventListener
  public void onApplicationEvent(ContextRefreshedEvent event) {
    log.info("Initializing data...");

    groupRepo.saveAll(getGroups());
    List<Group> allGroups = groupRepo.findAll();
    log.info("Created " + allGroups.size() + " groups");

    for (int i = 0; i < 100; i++) {
      User user =
          User.builder()
              .cognome(faker.name().firstName())
              .nome(faker.name().lastName())
              .username(faker.name().username())
              .group(randomElement(allGroups))
              .build();
      userRepo.save(user);
    }

    List<User> allUsers = userRepo.findAll();
    log.info("Created " + allUsers.size() + " users");

    for (int i = 0; i < 2000; i++) {
      User user = randomElement(allUsers);
      Protocollo protocollo =
          Protocollo.builder()
              .id(faker.code().ean13())
              .owner(faker.random().nextInt(10) < 7 ? user : null)
              .group(user.getGroup())
              .oggetto(faker.commerce().productName())
              .stato(StatoProtocollo.NUOVO)
              .build();
      protocolloRepo.save(protocollo);
    }
  }

  private <T> T randomElement(List<T> list) {
    return list.get(faker.random().nextInt(list.size()));
  }

  private List<Group> getGroups() {
    return Arrays.asList(
        Group.builder()
            .name("SVI")
            .childs(
                Arrays.asList(
                    Group.builder()
                        .name("Divisione Intermediari Italiani")
                        .childs(
                            Arrays.asList(
                                Group.builder().name("Gruppo 1").build(),
                                Group.builder().name("Gruppo 2").build(),
                                Group.builder().name("Gruppo 3").build()))
                        .build(),
                    Group.builder()
                        .name("Divisione Intermediari Esteri")
                        .childs(
                            Arrays.asList(
                                Group.builder().name("Gruppo 4").build(),
                                Group.builder().name("Gruppo 5").build()))
                        .build(),
                    Group.builder()
                        .name("Divisione Altri Intermediari")
                        .childs(
                            Arrays.asList(
                                Group.builder().name("Gruppo 6").build(),
                                Group.builder().name("Gruppo 7").build()))
                        .build()))
            .build(),
        Group.builder()
            .name("STC")
            .childs(
                Arrays.asList(
                    Group.builder()
                        .name("Divisione Vita")
                        .childs(
                            Arrays.asList(
                                Group.builder().name("Gruppo 8").build(),
                                Group.builder().name("Gruppo 9").build(),
                                Group.builder().name("Gruppo 10").build()))
                        .build(),
                    Group.builder()
                        .name("Divisione RCA")
                        .childs(
                            Arrays.asList(
                                Group.builder().name("Gruppo 11").build(),
                                Group.builder().name("Gruppo 12").build(),
                                Group.builder().name("Gruppo 13").build(),
                                Group.builder().name("Gruppo 14").build(),
                                Group.builder().name("Gruppo 15").build()))
                        .build(),
                    Group.builder()
                        .name("Divisione Infortuni")
                        .childs(
                            Arrays.asList(
                                Group.builder().name("Gruppo 16").build(),
                                Group.builder().name("Gruppo 17").build()))
                        .build()))
            .build(),
        Group.builder()
            .name("SGD")
            .childs(
                Arrays.asList(
                    Group.builder()
                        .name("Divisione Dati")
                        .childs(
                            Arrays.asList(
                                Group.builder().name("Gruppo Statistico").build(),
                                Group.builder().name("Gruppo Analisi").build()))
                        .build(),
                    Group.builder()
                        .name("Divisione Informatica")
                        .childs(Arrays.asList(Group.builder().name("Gruppo utenti").build()))
                        .build()))
            .build());
  }
}

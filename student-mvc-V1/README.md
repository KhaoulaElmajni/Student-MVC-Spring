
# <strong style="color:blue; opacity: 0.80">Activité Pratique Spring MVC, Spring Data JPA, Spring Security </strong>:mortar_board::computer: 
# <span style="color:green "> 1.Présentation de l'activité pratique</span>
 * <strong style="color:dark">Il s’agit d'une application Web basée sur Spring MVC, Spring Data JPA et Spring Security qui permet de gérer des étudiants. </span>
## <span style="color:#66ff66"> Entités et règles de gestion : :label:</span>
L’application devra gérer une entité. 
L'entité utilisée dans l’application est: 
* * * 
>	Une entité "Student" qui comporte les propriétés suivantes :
 - Son id
 - Son nom
 - Son prénom
 - Son email
 - Sa date naissance
 - Son genre : MASCULIN ou FEMININ
 - Un attribut qui indique si il est en règle ou non
```java=10
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Size(min = 3, max = 50)
    private String nom;
    @Size(min = 3, max = 50)
    private String prenom;
    private String email;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateNaissance;
    @Enumerated(EnumType.STRING)
    private Gender genre;
    private Boolean enRegle;
}

```

## <span style="color:#66ff66">  les fonctionnalités de l'application :label: </span>
L'application offre les fonctionnalités suivantes :


### 1. <span style="color:#001a33">Chercher des étudiants par nom.</span>
### 2. <span style="color:#001a33"> la pagination entre les une taille bien précis des étudiants.</span>
### 3. <span style="color:#001a33"> La suppression des étudiants en utilisant la méthode (DELETE au lieu de GET).</span>
### 4. <span style="color:#001a33"> Le Saisi et l'ajout des étudiants avec validation des formulaires.</span>
### 5. <span style="color:#001a33"> L'édition et la mis à jour des étudiants.</span>
### 6. <span style="color:#001a33"> La Création d'une page template.</span>

## <span style="color:#66ff66">La sécurité avec Spring Security :label: </span>
L'accès à l'application est sécurisée avec un système d'authentification basé sur Spring security en utilisant la stratégie User Details Service:

### La classe implémentation de UserDetailsService 'UserDetailsServiceImpl'

```java=10
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = securityService.loadUserByUsername(username);
        //en utilisant l'API des streams
        Collection<GrantedAuthority> authorities1 = appUser
                .getAppRoles()
                .stream()
                .map(role-> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());

        User user = new User(appUser.getUsername(),appUser.getPassword(),authorities1);
        return user;
    }
}

```
### La classe de configuration de la sécurité
```java=10
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //user details service auth
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin();
        //ne nécessite pas une auth
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/admin/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/user/**").hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers("/webjars/**").permitAll();
        /*gérer les droits d'accés*/
        //toutes les req nécessite une auth
        http.authorizeRequests().anyRequest().authenticated();
        //gestion des exceptions
        http.exceptionHandling().accessDeniedPage("/403");
    }
}

```

## <span style="color:#66ff66">Contraintes techniques :label: </span>
Toutes les opérations d’ajout, de modification et de suppression ne peuvent se faire que par un utilisateur authentifié et ayant le rôle "ADMIN". Les utilisateurs anonymes auront accès à la page index seulement et toute opération ne va pas aboutir son objectif.

# <span style="color:green">3.Les Technologies utilisées</span>
 #### <span style="color:#0036ad"> 1.Java</span>
 * <strong style="color:dark">* <strong style="color:dark">Java est le langage de choix pour créer des applications à l'aide de code managé qui peut s'exécuter sur des appareils mobiles.

*voir également à propos* [JAVA](https://www.java.com/fr/):link: 

 #### <span style="color:#0036ad"> 2.Spring MVC</span>
 * <strong style="color:dark">Spring MVC permet de construire des applications Web en Java. ... (MVC) en association avec le modèle IoC (Inversion of Control) du Spring Framework.

*voir également à propos de* [Spring MVC](https://gayerie.dev/epsi-b3-orm/spring_mvc/spring_mvc.html) :link: 

 #### <span style="color:#0036ad"> 3.Spring Data JPA</span>
 * <strong style="color:dark">Spring Data JPA, qui fait partie de la grande famille Spring Data, facilite la mise en œuvre de référentiels basés sur JPA. Ce module traite de la prise en charge améliorée des couches d'accès aux données basées sur JPA. Il facilite la création d'applications alimentées par Spring qui utilisent des technologies d'accès aux données.
    

*voir également à propos de [Spring Data JPA](https://spring.io/projects/spring-data-jpa) :link: 

#### <span style="color:#0036ad"> 4.MySQL</span>
 * <strong style="color:dark">est un système de gestion de base de données relationnelle (SGBDR) open source. Son nom est une combinaison de "My", le nom de la fille du co-fondateur Michael Widenius, et de "SQL", l'abréviation de Structured Query Language. Une base de données relationnelle organise les données en une ou plusieurs tables de données dans lesquelles les types de données peuvent être liés les uns aux autres ; ces relations aident à structurer les données. SQL est un langage utilisé par les programmeurs pour créer, modifier et extraire des données de la base de données relationnelle, ainsi que pour contrôler l'accès des utilisateurs à la base de données.
*voir également à propos* [MySQL](https://devdocs.io/css/) :link: 

#### <span style="color:#0036ad"> 5.Spring Security</span>
 * <strong style="color:dark">Spring Security est un cadre d'authentification et de contrôle d'accès puissant et hautement personnalisable. C'est la norme de facto pour sécuriser les applications basées sur Spring.

 * <strong style="color:dark">Spring Security est un framework qui se concentre sur l'authentification et l'autorisation des applications Java. Comme tous les projets Spring, la véritable puissance de Spring Security réside dans la facilité avec laquelle il peut être étendu pour répondre aux exigences personnalisées.
 
*voir également à propos de * [Spring Security](https://spring.io/projects/spring-security) :link: 

#### <span style="color:#0036ad"> 6.Thymeleaf</span>
 * <strong style="color:dark">Thymeleaf est un moteur de modèle Java XML/XHTML/HTML5 qui peut fonctionner à la fois dans des environnements Web et non Web. Il est mieux adapté pour servir XHTML/HTML5 au niveau de la couche d'affichage des applications Web basées sur MVC, mais il peut traiter n'importe quel fichier XML même dans des environnements hors ligne. Il fournit une intégration complète de Spring Framework.
    
*voir également à propos de * [Thymeleaf](https://www.thymeleaf.org/doc/articles/springsecurity.html) :link: 

 
## <span style="color:green ">4.Structure du projet</span>
![](https://i.imgur.com/lvrXRnI.png)


 ## <span style="color:green ">5.CONCEPTION & ANALYSES</span>
 * ###### <strong style="color:red; opacity: 0.80">Diagramme de classe </strong>
> Diagramme de classe [color=#fd837b]

  ---
  
   * ###### <strong style="color:red; opacity: 0.80">Diagramme de cas d'utilisation</strong>




 > Diagramme de cas d'utilisation [color=#fd837b]
  ---


 ## <strong style="color: green; opacity: 0.80" >6.comment ça marche ?</strong>
    
![](https://i.imgur.com/idqrG9f.png)
 > View de login par déafaut de Spring Security [color=#fd837b]
  ---
 
![](https://i.imgur.com/jKj7Kzw.png)
 > View home d'un utilisateur admin [color=#fd837b]
  ---

![](https://i.imgur.com/t4d0dNR.png)
 > Formulaire d'ajout d'un étudiant [color=#fd837b]
  ---
![](https://i.imgur.com/vaCF8QA.png)
 > Formulaire de mis à jour d'un étudiant [color=#fd837b]
  ---

![](https://i.imgur.com/8K4bH41.png)
 > View de consultation des données d'un étudiant [color=#fd837b]
  ---

![](https://i.imgur.com/aEkNGzC.png)
 > Cas de confirmation de suppression [color=#fd837b]
  ---

![](https://i.imgur.com/5GYk0W5.png)
 > recherche d'un étudiant par nom [color=#fd837b]
  ---

![](https://i.imgur.com/o256Mha.png)
 > View home d'un utilisateur qui a le role 'USER' qui a le droit uniquement de consulter les étudiants [color=#fd837b]
  ---


 
:::success
Voilà :white_check_mark: 
:::



* <strong style="color: dark ; opacity: 0.80">Enfin nous tenons à remercier le seul et unique, notre professeur Mr YOUSFI Mohamed *Docteur & professeur à l'ENSET MEDIA* pour son soutien et son encouragement envers nous, aussi pour nous avoir donné cette opportunité d'améliorer nos compétences et de connaître les nouvelles technologies comme celles qui nous avons travaillé.

*voir également à propos* Mr [YOUSSFI Mohamed](https://www.linkedin.com/in/mohamed-youssfi-3ab0811b/)
</strong>

> Created by :[name=ELMAJNI KHAOULA]
[time=Mon,2022,04,11][color=#EF0101]
>*voir également à propos de moi* [ELMAJNI Khaoula](https://www.linkedin.com/in/khaoula-elmajni/)
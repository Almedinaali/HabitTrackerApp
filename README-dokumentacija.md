# Dokumentacija za Završni Projekat – Habit Tracker aplikacija

>*Prirodno-matematički fakultet*  
>*Odsjek za matematiku*  
>*Predmet: Razvoj mobilnih aplikacija*  
>*Smjer: Teorijska kompjuterska nauka*  
>*Studenti: Almedina Alibašić 5528/M, Senida Smajlović 5425/M*  
>*Profesor: prof.dr. Elmedin Selmanović*
---

**1. UVOD**

Najjednostavnija definicija Androida je, da je to operativni sistem kojeg koriste mobilni uređaji. Nastao je prvenstveno iz razloga da se funkcionalnost ličnog računala što je moguće brže približi mobilnim uređajima tj. da sve što radimo na računaru, prilagodimo i na mobilnim aplikacijama. Android je otvorenog koda, i to je jedna od prednosti koje su ga izdvojile od konkurencije, ali nije to naravno jedina prednost ovog operativnog sustava. Time se omogućava razvoj bogatih i naprednih aplikacija u relativno kratkom vremenu uz nerijetko besplatnu podršku zajednice.

U informatičkoj nauci, aplikacija je skup računarskih programa dizajniranih za pomoć korisnicima u izvršavanju određenih zadataka. Sve aplikacije u Android-u su ravnopravne što znači da svaka aplikacija ima pristup istim resursima, pa je razvoj potpuno slobodan, tj. svaki korisnik može uređaj prilagoditi vlastitim potrebama.

Praktični dio ovog projekta je aplikacija HabitTracker za Android, napisana u programskom jeziku kotlin, koja služi za lične potrebe jednog korisnika.

1. **JAVA I KOTLIN**

Iako Android zbog DVM-a podržava sve programske jezike temeljene na JVM-u, trenutno najzastupljeniji programski jezici na Androidu su Java i Kotlin. Oba jezika pružaju objektno-orijentirane paradigme i mogućnosti poput čvrstog, statičnog sistema tipova, brzine i mogućnosti vrlo preciznog rada s višenitnosti, mrežom i bazama podataka. Java je godinama bila prvi izbor, ali zbog potrebe za optimizacijom izrade mobilnih aplikacija, lakoćom rada s podacima, proširivosti i zbog pojave modernih jezikovnih značajki poput lambda funkcija, ništavnih tipova (engl. nullable types) i tokova podataka (engl. streams), Kotlin polako preuzima titulu vodećeg jezika za razvoj Android aplikacija.

Sama Android platforma je pisana u Javi, ali povezivanje s Kotlinom nije problem, jer su Kotlin i Java u potpunosti interoperabilni. To znači da se Java kod može pokretati u Kotlin datotekama i obrnuto, bez dodatnog troška memorije ili vremena. Samim time aplikacije ne moraju biti u potpunosti prepisane u Kotlin, već se mogu prevoditi u koracima. Najvažnija značajka Kotlina je ta što njegove mogućnosti ne ovise o verziji Jave koja se koristi u projektima, kao ni o verziji Androida koja se podržava. Što je suprotan slučaj od Jave. Naime verzija Jave u projektu ovisi o najmanjoj podržanoj verziji Androida. Nijedna novija verzija, nije podržana prije Android Nougata (7.0), odnosno svega 43% uređaja podržava nove prevoditelje za JVM.

1. **AKTIVNOSTI I FRAGMENTI**

Da bi lakše opisali implementaciju aplikacije, prvo ćemo objasniti šta su to aktivnosti i fragmenti u android aplikacijama.

Životnim ciklusom aktivnosti upravlja se sljedećim naredbama:

- onCreate (Bundle)- poziva se prilikom pokretanja aplikacije,

- onStart ()- označava početak prikaza aplikacije korisniku,

- onResume ()- poziva se prilikom početka ili nakon nastavka interakcije s korisnikom,

- onPause ()- poziva se prilikom prelaska u pozadinski način rada,

- onStop ()- poziva se u slučaju dužeg perioda nekorištenja aplikacije,

- onRestart ()- označava ponovno pozivanje aplikacije iz zaustavljenog stanja,

- onDestroy ()- poziva se trenutak prije gašenja aplikacije,

- onSaveInstanceState (Bundle)- opcionalna metoda koja se poziva u slučaju očuvanja stanja i
- onRestoreInstanceState (Bundle)- poziva se prilikom ponovnog pokretanja aktivnosti iz stanja prethodno pohranjenog onSaveInstanceState () metodom.

Fragment (eng. fragmet) predstavlja dio korisničkog sučelja u aktivnosti. Možemo ga zamisliti kao mini-aktivnost koja ima svoj životni ciklus, ali za razliku od standardnih aktivnosti ne može se prikazivati zasebno, tj. da bi se prikazao fragment, mora se dodati u standardnu aktivnost. Fragment možemo dodati odnosno ukloniti dok se aktivnost izvodi, te on izravno utječe na životni ciklus aktivnosti. Uzmimo za primjer da je aktivnost pauzirana, tada je i fragment u njoj pauziran, a kada je uništena i fragmenti su uništeni. Dok je aktivnost pokrenuta, fragmenti u aktivnosti se mogu dodavati odnosno uklanjati.Fragment kao i aktivnost je definiran određenom java klasom, a korisničko sučelje učitava iz određene XML datoteke.

Glavne karakteristika fragmenta

- enkapsuliraju dijelove korisničkog sučelja

- mogu se jednostavno ponovo koristiti

- dinamički se mogu dodavati i uklanjati

- sučelje se lakše prilagođavaju uređaju

U implementaciji ove aplikacije korištena je jedna aktivnost MainActivity, a za sve ostale potrebe aplikacije korišeni su fragmenti.

1. **BAZA PODATAKA**

Za potrebe čuvanja podataka naše aplikacije korištena je Room baza podataka. Ustvari

Room je biblioteka koja pruža sloj apstrakcije nad SQLite bazom podataka i time omogućava jednostavan pristup bazi podataka i iskorištavanje punog potencijala SQLite baze podataka. Room se sastoji od 3 glavne komponente:

• Database – glavna komponenta koja služi kao pristupna točka preko koje se odvijaju sve radnje kao što su umetanje, dohvaćanje, modificiranje i brisanja podataka i sadrži reference na DAO objekte

• Entity – komponenta koja predstavlja tablicu određenih entiteta

• DAO (Data Access Object) – komponente preko kojih se obavljaju radnje umetanja, dohvaćanja, ažuriranja i brisanja podataka

I komponentu Database i komponente DAO implementiramo apstraktno – komponenta Database se implementira kao apstraktna klasa dok se DAO objekti implementiraju kao interface. Na klasu koja modelira komponentu Database potrebno je staviti bilješku (engl. annotation) „@Database&quot;, dok na DAO interface stavljamo bilješke „@Dao&quot;. Uz anotaciju „@Database&quot; potrebno je staviti popis klasa koje modeliraju entitete koji će biti sadržani u bazi kao i verziju baze.

Metode DAO sučelja mogu se označiti bilješkama „@Insert&quot;, „@Query&quot;, „@Delete&quot; ili &quot;@Update&quot; ovisno o radnji koju želimo izvesti.

Uz bilješku „@Query&quot; potrebno je napisati i SQL upit kojim se označava koje podatke želimo dohvatiti.Jedine komponente koje nisu apstraktne su komponente Entity – njih se može označiti bilješkama „@Entity&quot; i implementirati ih kao obične java klase.

Uz bilješku „@Entity&quot; se mogu dodati atributi kao što su naziv tablice entiteta, popis primarnih ključeva, popis stranih ključeva te popis indeksa. Kako bi DAO objekti upitom mogli vratiti entitete njihove varijable moraju biti javne ili mogu biti privatne s implementiranim metodama za postavljanje i dohvaćanje pojedinih varijabli. Dodatno, pojedine varijable mogu se obilježiti bilješkama „@PrimaryKey&quot;, da bi se naznačilo da je varijabla primarni ključ entiteta, „@ColumnInfo&quot;, da bi se dodale dodatne informacije o stupcu u bazi, na primjer ime stupca ili „@Ignore&quot; kako bi se naznačilo da se ta varijabla ne sprema u bazu.

Pošto su i DAO i Database komponente apstraktne, prilikom prevođenja Room će sam stvoriti implementacije tih objekata koji će implementirati modeliranu bazu pomoću SQLite baze podataka.

Pristupanje implementiranim metodama je jednostavno. Za početak, potrebno je stvoriti instancu Database komponente. To se radi pozivanjem statičke metode „Room.databaseBuilder&quot; kojoj se predaje kontekst, klasa Database komponente te ime baze. Metoda vraća konkretnu implementaciju Database komponente koja sadrži konkretne implementacije DAO komponenti koje su navedene unutar nje. Zbog Liskovina načela supstitucije konkretna implementacija se može spremiti u referencu koja je apstraktnog tipa.

1. **HABIT-TRACKER APLIKACIJA**

Nakon što smo opisali potrebne pojmove za razvoj android aplikacija, sada možemo preći na opis arhitekture i načina rada naše aplikacije.

Baza podataka za našu aplikaciju sastoji se od entiteta koji se nalaze u folderu _ **entity.** _ Svaki entitet ima odgovarajuću DAO komponentu preko koje pristupamo entitetu. Sama instanca baze implementirana je u klasi _ **AppDatabase,** _ i ova instanca je jedinstvena tj. nakon što je kreirana prvi put. Svaki naredni put se poziva ta ista instanca što spriječava otvaranje, upisivanje, brisanje u bazi na više mjesta što bi pri velikim upotrebama dovelo do zabušenja.

Dakle, za potrebe aplikacije dalje koristimo instancu klase AppDatabase.

Aplikacija ima jednu aktivnost, _ **MainActivity.** _ U _MainActivity_ u funkciji _onCreate()_ su kreirani toolbar, Navigation drawer i Bottom Navigation. Navigation drawer je kreiran pomoću klase _ActionBarDrawerToggle._ To je klasa koja pruža praktičan način za povezivanje funkcionalnosti _DrawerLayout_-a i _ActionBar_-a. Navigation Drawer omogućava pristup sljedećim komponentama aplikacije: _Početna stranica, Postignuća, Napomene, Postavke_ i _Podijeli_, dok Bottom Navigation omogućava brz pristup Početnoj stranici, Kategorijama i Profilu. Također, ova aktivnost služi kao domaćin svim fragmentima aplikacije.

Kada korisnik pokrene aplikaciju otvara mu se stranica _ **Kategorije** _, čija se implementacija nalazi u klasi _ **KategorijeFragment.** _ Ovdje je prikazan spisak svih dosad unešenih kategorija u bazu podataka, što je urađeno preko adaptera _ **KategorijeAdapter** _. Adapter nam omogućava jedinstven ispis svakog elementa proslijeđene mu liste, tako da ne ovisi o dužini liste. U ovom slučaju proslijeđena mu je lista svih kategorija.

Također korisnik ima opciju dodavanja nove kategorije klikom na dugme u donjem desnom dijelu stranice. Nakon klika na dugme učitava se fragment _ **DodajKategorijuFragment** _ koji koristi layout _ **fragment\_dodaj\_kategoriju.** _ Učitavanjem ovog fragmenta korisniku se nudi mogućnost unosa podataka za novu kategoriju, gdje upisuje naziv kategorije (obavezan), osobinu i bira tip kategorije. Nakon što su uneseni podaci klikom na dugme _ **DODAJ** _ u bazu se upisuje nova kategorija a korisnik je vraćen na stranicu _ **Kategorije.** _ Ukoliko prije dugmića _ **DODAJ** _ korisnik pritisne tipku za vraćanje, samo će biti vraćen na stranicu _ **Kategorije** _ bez ikakvih akcija.

Klikom na neku od kategorija otvara se stranica te kategorije sa spiskom svih aktivnosti koje pripadaju toj kategoriji, što je regulisano funkcijom _**onElementClick()**_ preko koje se učitava fragment _ **AktivnostiFragment.** _ Koji slično kao i _ **KategorijeFragment** _, pomoću adaptera _ **AktivnostiAdapter** _ ispisuje sve aktivnosti koje pripadaju otvorenoj kategoriji.

U ovom dijelu aplikacije korisnik ima opciju dodavanja nove aktivnosti klikom na dugme _ **DODAJ** _, i brisanja čitave kategorije klikom na dugme _ **IZBRISI** _. Ukoliko korisnik klikne na dugme _ **IZBRISI** _ dobija poruku upozorenja, jer prilikom brisanja kategorije brišu se i sve aktivnosti te kategorije. Ako ipak odluči da nastavi bit će vraćen na stranicu _ **Kategorije** _ stim da će izbrisana kategorija biti uklonjena iz baze, pa samim tim i sa spiska kategorija.

Ako korisnik odluči da doda novu aktivnost, otvorit će se novi fragment _ **DodajNovuAktivnostFragment** _, gdje može da unese potrebne podatke i da doda aktivnost ili da odustane. Dodavanjem aktivnosti, nova aktivnost se dodaje u bazu, a korisnik je vidi prikazanu na spisku aktivnosti.

Klikom na neku od aktivnosti, otvara se _ **FragmentDialog\_ZaAktivnost** _, u kojem se korisniku nudi mogućnost prepravljanja podataka te aktivnosti, nakon čega može da sačuva izmjene ili da izbrise tu aktivnost - tada će biti vraćen na stranicu Aktivnosti sa poduzetim potrebnim radnjama koje je odabrao.

Sljedeća stranica je _ **Početna stranica** _, čija se implementacija nalazi u _ **PocetnaFragment** _. Na ovoj stranici se odvija bilježenje i praćenje aktivnosti. Prilikom prvog pristupa aplikaciji, korisniku su prikazane dvije početne aktivnosti. Klikom na _Floating Action Button_ koji se nalazi u donjem desnom uglu, korisniku se prikazuje _ **Alert** __ **Dialog** _s listom svih postojećih aktivnosti u bazi. Aktivnosti koje korisnik odabere, pritiskom na dugme OK, dodaju se na _Početnu stranicu._

Postoje tri različita tipa aktivnost pa svaka aktivnost ima zaseban layout .xml file: _ **pocetna\_inkrementalna\_element.xml, pocetna\_vremenska\_element.xml, pocetna\_kolicinska\_element.xml** _. Svaki element sadrži naziv aktivnosti, dugme/dugmad i unos. Pored toga, inkrementalne i količinske aktivnosti još sadrže i mjernu jedinicu. Element inkrementalnog tipa aktivnosti ima dva dugmeta: + i -. Klikom na dugme plus, poziva se funkcija _povecajInkrement_ te se povećava broj onoga što se mjeri za inkrement koji je unesen prilikom kreiranja odgovarajuće aktivnosti. Klik na dugme minus radi suprotno, poziva funkciju _smanjiInkrement_ te se smanjuje broj onoga što se mjeri. Aktivnost količinskog tipa ima jedno dugme: _Dodaj_ i klikom na to dugme korisniku se prikazuje polje za unos količine koju želimo dodati za datu aktivnost. To se izvršava pozivanjem funkcije _unesiKolicinu_, a polje za unos je kreirano pomoću Alert Dialog-a. Količina koju korisnik unese se sprema u bazu i prikazuje kao unos za odgovarajuću aktivnost. Aktivnosti vremenskog tipa imaju jedno dugme: _Započni_. Klik na to dugme započinje mjerenje vremena i dugme _Započni_ se pretvara u dugme _Zaustavi_. Mjerenje vremena se nastavlja sve dok korisnik ne pritisne dugme _Zaustavi,_ nakon čega se proteklo vrijeme između klika na _Započni_ i _Zaustavi_ pohranjuje u bazu. Vrijeme koje je proteklo ostaje prikazano za tu aktivnost sve dok korisnik ponovo ne pritisne dugme _Započni_, nakon čega štoperica ponovo kreće mjerenje vremena od 00:00. Za pokretanje štoperice zadužena je funkcija _zapocniStopericu_ i za implementaciju štoperice korišten je _ **Chronometer** _. Chronometer je klasa koja implementira jednostavan timer. Pomoću _chronometer.setBase(SystemClock.elapsedRealtime())_ postavljamo da mjerenje vremena uvijek započinje od 00:00, a metode ._start()_ i ._stop()_ su zadužene za pokretanje i zaustavljanje štoperice. Za prikaz aktivnosti na Početnoj stranici korišten je _RecyclerView_ te odgovarajući adapter _ **PocetneAktivnostiAdapter** _. Pošto svaki tip aktivnosti ima zaseban .xml fajl u adapteru se nalaze tri viewHoldera: _InkrementalneViewHolder_, _KolicinskeViewHolder_ i _VremenskeViewHolder_, za aktivnosti tipa: inkrementalne, količinske i vremenske redom.

**3. ZAKLJUČAK**

Pokretni uređaji postali su svakodnevni alati koje koristi veliki broj ljudi, različitih godina i zanimanja. Oni više ne predstavljaju luksuz, već su dostupni svima. U oblasti pokretnih uređaja, Android je vodeći operacijski sustav već nekoliko godina, što ga čini prvim izborom svakog korisnika koji bi htjeli da koriste jedan ovakav uređaj. Činjenica da je to open-source operacijski sistem, čini ga idealnom platformom za kreiranje najrazličitijih aplikacija, i korištenje svih funkcionalnosti koje najnovije tehnologije pružaju. U korist sistemu ide i bogata baza biblioteka te funkcionalni alati za izradu aplikacija. Uz aktivna poboljšanja razvojne okoline za pisanje aplikacija i programskih biblioteka, Android predstavlja operacijski sistem koji obećava ostaviti značajni trag u svijetu razvoja programske podrške, ne samo za pokretne uređaje već za male uređaje općenito.

U ovom radu opisan je razvoj Android aplikacije &quot;Habit Tracker&quot;. Kroz razvoj aplikacije prikazana je upotreba nekih od funkcija i metoda koje se koriste kod izrada Android aplikacija. Pojašnjeni su pojmovi vezani za Android, kao što su njegova arhitektura, aplikacijske komponente koje čine samu aplikaciju.
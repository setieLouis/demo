 =============
| Docker tuto | 
 =============


#Container
 ---------
 Puo essere visto come un  processo avendo il proprio ambiente e quindi il prioprio file system, l'insieme delle librerie 
 necessarie e compilatori per eseguire un certo   (progetto java ad esempio)
 quindi e una embiente protetto prtatile per eseguire un software in quel senso diverso di una macchina virtuale


 un container quindi si trova 
 a) un una distrubution light di linux o JeOS (Just Enought Operating System), cioe una versione ridotta di sistema operativo che serve per poter 
    aver un abiente di esecuzione 
 b) Librairie de dipendenze 

 c) ambiente di esecuzione ad esempio nel caso di un software java si avra bisogno della JVM 
 d) Librerie di dipendenze relattive all'applicazione exempio framework ecc.. ad esempio un progetto server java si puo usare avere spring , hibernate...
 e) ilk codice dell' applicazione che si vuole eseguire



 Exampio  di 3 diversi container dove ogni riga si chiama layer
 
  
  ---------------   --------------------  --------------            ------------------    
  Ruby code         javascript code       .ear file  			    Applicazione code 
  --------------    --------------------  --------------  		    -----------------
  Gems              node_modules          Tomcat 		  ========>	Dipendencies 
  --------------    --------------------  --------------  REASSUNTO ------------------
  Ruby runtime		Node.js runtime		  JRE						Runtime 
  --------------    --------------------  --------------			------------------
  Ubuntu			Debian	 			  CentOS					JeOS


*** Le NameSpaces
Sono in 6 e servono per definire le caratteristiche del container 
	1) pid (process id ) : serve l'id del processo che è il container quindi isolarlo dagli altri processi
	2) net : permette di definire una pila di rete privata 
	3) mount : permette di definire (montare un nuovo) file system diverso da quello sul sistema ospitante
	4)  uts : permette di definire il nome dell host del container
	5) ipc : usato come pipline tra i processi
	6) permette di definire i permessi degli utenti nel container


*** Cgroup (da vedere) serve per defire il risorse della macchina a cui ol container ha accesso




Commandi base (avviare prima docker e andare sul terminale  su windows usare o git bash o quache altro tool che emula il terminale di linux) 
-------------

*** Creazione di imagine 
$ docker run alpine  // serve a creare un container basato sulla distribution linux alpine 
$ docher run alpine echo cioa// quind fa la stessa cosa e all'interno di alpine esegue il scirpt "echo  ciao" +

vedere le option del commando run : https://docs.docker.com/engine/reference/commandline/run/

alpine in questo quindi e un imagine che viene scaricato da docker se non presente gia su docker il locale 
dopo aver esguito il commando su terminale vengono stampati dei lo che informano su cosa viene fatto effettivamente

==> per intrare nella shell dei JeOs basta eseguire run con argomento ti 
ocker run alpine 

esempio 

acount$ docker run -ti alpine 
/#  echo ciao   // qui siamo quindi nella shell dell alpine dentro il container e possiamo  eseguire i script base di linux quali ls , echo ... 
Cioa

i commandi che si posso eseguire dipende dal tipo d'imagine cioe i programmi che ci sono preinstallati . chiaro a bisogno si posso arrichire installando 
nuovi pachetti ad esempio in con un imagine si potra usate apt-get per installare nuovi pachetti 


===> -d ad esempio si lancia il container in background e viene ritornato il suo id 
===> -p serve esporre un porta del contaier alla machina host  esxempio 
docker run -d -p 8080:80 nginx // sul qualche browser della macchina host il  esecuzione sara accessibile loaclhost:8080 
 per default nginx e su la porta 80


 Commandi per il ciclo di vita del container 
 -------------------------------------------
** considerando che prima di bisogna inserire la docker container
==> ls : serve per elencare i container attivi  dando altri informazioni quali ad esempio STATUS ...
	==> -a cosi vediamo anche i container fermati 
	==> -q lista gli Id dei container attivi 

	*** notare che  si possono usare insieme qundi ad esempio ls -aq   lista gli  id dei resultati tutti container 
	
==> ps permette di vedere i container attivi 


==> inspect : permette di fare un inspezione del container. dopo il commano (usato non solo con i container )
	docker container inspect idContainer // ci manda un gigante json contenente tutte le info sul nostro container 
	==> --format '{{ json .Id}}' // ritorna solo l'id della container 
		quindi usando questo criterio ci basta indicare --format {{json .Chiave }} dove chiave è il sotto gruppo che vogliamo recuperare

==> log : permette di vedere i log in stout 
	==> -f ci permette ad esempio i log di un container eseguito in background
	esempio : acount$ docker run -d  alpine ping 8.8.8.8
			  idContainer 
			  acount$ docker container logs idContainer 
			  64 bytes from 8.8.8.8: seq=0 ttl=37 time=10.514 ms
			  64 bytes from 8.8.8.8: seq=1 ttl=37 time=11.818 ms
			  64 bytes from 8.8.8.8: seq=2 ttl=37 time=12.492 ms
			  acount$ docker container logs idContainer 
			  64 bytes from 8.8.8.8: seq=51 ttl=37 time=11.673 ms
			  64 bytes from 8.8.8.8: seq=52 ttl=37 time=7.953 ms
			  64 bytes from 8.8.8.8: seq=53 ttl=37 time=7.203 ms
			  64 bytes from 8.8.8.8: seq=54 ttl=37 time=8.945 ms
			  ...
			   per terminare basta crtl c

==> exec : permette di  eseguire un comando in container durante la sua esecuzione e viene tante volte per il debug 
	ad esmpio  docker container exec -ti idContainer sh 
	/#  in questo caso se nella shell alpine lanciamo di il commando ps 
	possimo notare che in ping e tra i process
	quindi con exec possiamo fare un espensione un runtimde 


==> stop permette di  fermare i container in esecuzione. com parametro prende un id o un llista di id 
	esempio acount$ docker container stop idContaner 
			acount$ docker container stop $(docker container ls -q )

==> rm funziona in mod simile al stop solo che elimina i contaner 
	==> permtte di fermare e cancellare il container di cui l'id è dato in pasto 
	esempio docker container rm -f $(docker container ls -aq) 
	elimina tutti i container presenti 

play with docker  basta googlarlo
-----------------
è un terminale online che permtte testare i commandi docker 





#Image 
---------
una imagine puo esere cosideranto il contenut del container.
puo visto come il pregetto congelato in certo tempo che possaimo 
mandare in deploy. quindi un imagine

cos'è un iamgine 
----------------
E' un insieme di strati (layer) dove ogni per il suo funzionamento dipende degli strati sottostanti stratti parenti, eccetto il primo stratto che il JeOS


Application code    Layer 5  read only
-----------------
code dipendencies 	Layer 4	 read only
-----------------
Runtime engine      Layer 2	 read only
-----------------
os Librairie        Layer 1  read only
-----------------
Os 					Layer 0  read only
------------------
quindi raprensta  una release (idealmente) funzionante nel nostro progetto nell ambiente protetto 


 ===>  Layer  esempio un  war o jar  java 
 un imagine è composto di Layer dove un layer per il suo funzionamento ha bisogno del layer sottostante 
 un volta inseriti i layer non  piu modificabili.
 tutte le modifiche che avengono sul prgetto ad gli input di un form da salvare nel database vanno sul l'ultimo 
 layer che viene inserito a docker   

 *** quando un container è estanziato tutti layer vengono fusi insieme per creare un layer unico e viene 
 aggiunto un layer in lettura e in scrittura cosi da salvare tutte le modifiche che avvengono dopo il deploy 


update layer          Layer6 read and write 
--------------------
progetto complessivo  fusion dei layer precendente  
---------------------

*** notare che ogni volta che un container è eliminato il sui writable layer è anche esso sopresso ed questo e viene 
usato il volume per salvate i dati persistenti  


#Crezione di imagine 
___________________

Ci sono due modi per creare un immagine 
---------------------------------------

1) tramite il commit : cos


2) docker file 
---------------
è un fime che permette la definizione  di imagine da poter eseguire. 
cioè invece di fare "docker run alpine" potremo creare la nostra imagine 
runnarla

il docker quindi consite creare un file chiamato "Dockerfile" senza extenzione e dinire i layer che vogliamo include nella nostra imagine tramire i seguente  Commandi :

- FROM : permette di importare lo strato di Os (distribuzione di Linux)
- MAINTEINER : per definire informazione sui chi mantiene il l'imagine mail, tel , nome ...
- Run permette di eseguite commandi Unix/Linux all'interno dell imagine 
- EXPOSE : permette di espore un porta dell'immagine che si puo collegare ad una delle porte 
			delle macchine sottostanti nel caso asempio di server nginx
- COPY : permette di di coppiare una rissorsa dalla macchina locale verso l'imagine 
- ENTRYPOINT: E' wrapper per l'applicazione. permette di definire un commando da  eseguire 					 duramte il processo di running dell'applicazione 

- CMD : con la entry point permette di definire l'argomento dell commando della entry point.
		nel caso mon soa definita la ENTRYPOINF 
-VOLUME : il volume è un path della macchina loacale in cui vengono salvati le modifiche che 				avengono sull'imagine (layer write della nostra imagine)

-WORKDIR : il path della diretorie all'interno dell'imagine dove trovare i file da eseguire 


* notare che il docker a ruolo di creare un immagine e non fa altro altro 
** Commandi per manipoalre le immaginin 
----------------------------------------
- docker image
		-- build   /pathDockerfile
		   -- -t : tag permette  di definire il tag dell'amagine 
		   -- -f : permette di definire il file dockerFile
		-- ls per vedere lista d'immagini
		-- inspect per inspezionare l'imagine 
		-- rm id o nome imagine per cancellarlo 
** testare il resto dei commandi
** l'imagine va eseguito come qualsiasi imagine 

















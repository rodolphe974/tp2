import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class SeleniumTest {
    //SETUP
    WebDriver driver;

    @Before     //permet de préparer les éléments communs à tous les tests
    public void setup(){

        //on créer un string pour stocker le nom du navigateur passé en argument bash
        String browser = System.getProperty("browser");
        if (browser == null)
            //on va travailler dans chrome
            driver = new ChromeDriver();
        else if(browser.equals("firefox"))
            //on va travailler dans chrome
            driver = new FirefoxDriver();
        else
            driver = new ChromeDriver();

        //on demande l'activation de implicitwait
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // ouvrir la page google.fr
        driver.get("https://www.google.com");

    }

    // TEARDOWN
    @After // permet d'indiquer ce qu'on fera de façon systématique après chaque tests
    public void teardown(){
        // driver.close();     //ferme un onglet mais n'arrête pas le processus chrome (peu ou pas utilisé)
        driver.quit();      //ferme chrome proprement (il vaut mieux ça que driver.close()
    }

    // TEST
    @Test // cas de test avec text + entree
    public void testEnter()throws InterruptedException
    {
        //déclaration des éléments dont on va avoir besoin
        WebElement barreRecherche;
        WebElement premierResultat;
        String expected = "République française - France — Wikipédia";

        //repérer le champ de recherche google (on cherche un id unique dans la page puis :)
        barreRecherche = driver.findElement(By.id("lst-ib"));
        //saisir le texte "canelé"
        barreRecherche.sendKeys("france");
        //taper entree
        barreRecherche.sendKeys(Keys.ENTER);
        //Thread.sleep(1000);
        //on s'intéresse au résultat

        //on peut recherche le premier lien affiché, par son xpath (pas ideal car peut changer d'un moment à l'autre)
        //(en manuel on clique droit sur le lien > inspecter puis on clique droit sur le code > copier > xpath)
        //premierResultat = driver.findElement(By.xpath("//*[@id=\"rso\"]/div[2]/div/div/div/h3/a"));

        //on peut aussi faire une recherche en utilisant CSS selector
        //cela consiste à reperer la class de l'objet  puis l'element qui nous intéresse
        //pour selectionner une classe on utilise l'opérateur ".", pour une classe fille on utilise ">"
        //si l'objet comporte 2 classe on fait par exemple ".rc.r"
        premierResultat = driver.findElement(By.cssSelector(".rc>.r>a"));
        Assert.assertEquals(expected, premierResultat.getText());

        //juste pour l'exercie on va faire une pause de 1sec mais évidemment en automatisation on ne le fera pas
        Thread.sleep(1000);
    }

    @Test // cas de test avec text + clic que bouton rechercher
    public void testClick ()throws InterruptedException
    {
        //déclaration des éléments que l'on va recherche dans la page
        WebElement barreRecherche;
        WebElement boutonRecherche;
        String expectedText ="Recette de Canelés Bordelais rapides : la recette facile";
        WebElement premierresultat ;

        //repérer le champ de recherche google (on cherche un id unique dans la page puis :)
        barreRecherche = driver.findElement(By.id("lst-ib"));
        //déclaration des éléments que l'on va recherche dans la page
        //saisir le texte "canelé"
        barreRecherche.sendKeys("canelé");

        //synchronisation : on attend que le menu déroulant apparaisse avec le bouton recherche google
        //on peut le faire avec Thread.sleep pour fixer un temps (pas ideal)
        //ou alors on utilise ImplicitWait (mieux) mais qui ne fonctionne que pour contrer l'exception NoSuchElement de findElement
        //ou alors on utilise un ExplicitWait (j'attend y secondes ou jusqu'à ce que l'élément x soit visible/présent/disparu)
        //Thread.sleep(1000);

        //reperer le bouton "Recherche Google"
        //il y a plusieur objets de mm classe et pas id unique...mais celui qu'on cherche est le premier donc
        boutonRecherche = driver.findElement(By.className("lsb"));
        //on clique dessus
        boutonRecherche.click();

        //recherche du premier résultat affiché dans la page par son xpath
        premierresultat = driver.findElement(By.xpath("//*[@id=\"rso\"]/div[1]/div/div/div/h3/a"));
        Assert.assertEquals(expectedText, premierresultat.getText());

        //juste pour l'exercie on va faire une pause de 1sec mais évidemment en automatisation on ne le fera pas
        Thread.sleep(1000);
    }
}

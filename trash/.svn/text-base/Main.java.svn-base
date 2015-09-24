import java.io.IOException;
import java.util.List;

import split.srx.Document;
import split.srx.Parser;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String text = "Przesyłam wam wersje jaką wysłałem. Jako że nikt nie zgłaszał poprawek (poza Grzegorzem z którym godzinkę pogadałem) poszło prawie identyczne jak w niedziele. Jeśli w waszym fragmencie (albo ogólnie) diagram wydaje wam się bezsensowny to będziemy musieli go jeszcze dalej w toku prac zmieniać, bo i tak musimy dobrze pojąć dziedzine jeśli mamy stworzyć porządny projekt. Miejmy nadzieje że nam to zaliczą (bo rysowanie modeli dziedziny to jeszcze nie nauka ścisła, można to zrobić na mnóśtwo sposobów poprawnie, ważne żeby nam to się przydało), ale jeśli dalej nasza współpraca będzie tak wyglądać to projekt będzie beznadziejny. Pamiętajcie że było nie było jesteśmy jednym zespołem i robimy projekt wspólnie więc wszelka dyskusja jest jak najbardziej wskazana. Licze na to że w dalszych częsciach uda nam się problem jasno między nas rozdzielić i ewentualnie dogadywać miejsca w których nasze podzadania będą się łączyć (pewnie  niestety będzie ich dużo jak zwykle), bo taka forma pracy widać zawodzi. Dla mnie ten porzedmiot jest trochę nudny i też mi się nie chce nim zajmować (bo to w sumie \"ładne rysunki\", nie wiadmo jak to się sprawdzi w praktyce), ale myśle że teraz będzie już coraz lepiej bo zaczniemy się zajmować nauką bardziej ścisłą od analizy - projektowaniem, gdzie będą czasem jasne wzorce jak coś zrobić a nie tylko domysły.";
			Document document = Parser.getInstance().parse("data/test/polsplit.srx");
			List<String> list = document.split(text, "PL_pl", "Default");
			System.out.println(list);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlException e) {
			e.printStackTrace();
		}
	}

}

package headings;

import java.util.Arrays;
import java.util.List;

public class TestOutLine {

	public static void main(String[] args) {
		List<Heading> example = Arrays.asList(
				new Heading(1, "All About Birds"),
				new Heading(2, "Kinds of Birds"),
				new Heading(3, "The Finch"),
				new Heading(3, "The Swan"),
				new Heading(2, "Habitats"),
				new Heading(5, "The Rabbit"),
				new Heading(3, "Wetlands"),
				new Heading(3, "Wetlands 2"),
				new Heading(2, "Desert"),
				new Heading(1, "Cactus"),
				new Heading(1, "All About Dragons")

		);
		
		List<Heading> example2 = Arrays.asList(
				new Heading(1, "All About Birds"),
				new Heading(2, "Kinds of Birds"),
				new Heading(3, "Song Birds"),
				new Heading(4, "Bluejay"),
				new Heading(4, "Cardinal"),
				new Heading(4, "House Sparrow"),
				new Heading(5, "Eating Habits of Sparrow"),
				new Heading(3, "WaterFowl"),
				new Heading(4, "The Swan"),
				new Heading(4, "Heron"),
				new Heading(5, "Heron facts"),
				new Heading(5, "Heron facts II "),
				new Heading(4, "Ducks"),
				new Heading(4, "Coot"),
				new Heading(4, "Greb"),
				new Heading(4, "Geese"),
				new Heading(5, "The Grey Goose"),
				new Heading(5, "The Canadian Goose"),
				new Heading(2, "Areas birds live"),
				new Heading(3, "Wetlands"),
				new Heading(3, "More Wetlands "),
				new Heading(3, "The sorta wetlands"),
				new Heading(3, "Desert"),
				new Heading(4, "Cactus in the desert"),
				new Heading(4, "All About desert Dragons")

		);
		
		Node solution = Outline.outline(example2);
		solution.print();
	
		

	}

}

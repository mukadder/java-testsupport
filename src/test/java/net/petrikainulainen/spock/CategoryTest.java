package net.petrikainulainen.spock;

import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.smalaca.enumeration.designpatterns.Category;

@RunWith(Parameterized.class)
public class CategoryTest {
 
    private Category category;
    private String description;
    private String example;
  
    public CategoryTest(Category category, String description, String example) {
        this.category = category;
        this.description = description;
        this.example = example;
    }
  
    @Parameters
    public static Collection<Object[]> dataProvider() {
    return Arrays.asList(new Object[][] {
            { 
                Category.CREATIONAL, 
                "Deals with object creation mechanisms, trying to create objects in a manner suitable to the situation.",
                "Builder"
            },
            { 
                Category.STRUCTURAL, 
                "Eases the design by identifying a simple way to realize relationships between entities.",
                "Adapter"
            },
            { 
                Category.BEHAVIORAL, 
                "Identifies common communication patterns between objects and realize these patterns.",
                "Strategy"
            }
        });
    }
  
    @Test
    public void getCategoryExplanation()
    {
        assertSame(description, category.describe());
    }
  
    @Test
    public void getExample()
    {
        assertSame(example, category.getExample());
    }
}
package com.github.devishankar.utsample;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.filters.FilterClassName;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PojoTest {
    private String packageName = "com.github.devishankar.utsample";
    private Validator validator;
    private List<PojoClass> pojoClasses;

    @Before
    public void validate() {
        //Can include more class names with using `|`. e.g. \w*Model$|\w*Pojo$
        pojoClasses = PojoClassFactory.getPojoClassesRecursively(packageName, new FilterClassName("\\w*Model$"));

        validator = ValidatorBuilder.create().with(new SetterTester(),
                new GetterTester()).build();
    }

    @Test
    public void test_PojoStructureAndBehavior() {
        validator.validate(pojoClasses);
    }
}

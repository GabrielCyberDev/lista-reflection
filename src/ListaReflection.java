import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

public class ListaReflection {

    // Atributo do tipo lista (não é instanciado com new)
    private List<Object> lista;

    public void executar() throws Exception {
        // 1) Instanciar a lista via reflection
        Class<?> classeLista = Class.forName("java.util.ArrayList");
        Constructor<?> construtor = classeLista.getDeclaredConstructor();
        Object novaLista = construtor.newInstance();

        // Guarda a lista no atributo via reflection também
        Field campo = ListaReflection.class.getDeclaredField("lista");
        campo.setAccessible(true);
        campo.set(this, novaLista);

        Object listaAtual = campo.get(this);

        // 2) Adicionar objetos invocando o método add via reflection
        Method add = classeLista.getMethod("add", Object.class);
        add.invoke(listaAtual, "Java");
        add.invoke(listaAtual, "Reflection");
        add.invoke(listaAtual, 2026);
        add.invoke(listaAtual, "EBAC");

        // 3) Iterar via reflection: iterator(), hasNext() e next()
        Method iterator = classeLista.getMethod("iterator");
        Object it = iterator.invoke(listaAtual);

        Method hasNext = Iterator.class.getMethod("hasNext");
        Method next = Iterator.class.getMethod("next");

        System.out.println("Valores da lista (obtidos via reflection):");
        while ((boolean) hasNext.invoke(it)) {
            Object valor = next.invoke(it);
            System.out.println(" - " + valor);
        }
    }

    public static void main(String[] args) throws Exception {
        new ListaReflection().executar();
    }
}
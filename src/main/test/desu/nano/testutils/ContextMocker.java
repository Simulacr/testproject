package desu.nano.testutils;


import javax.faces.context.FacesContext;

import org.mockito.Mockito;
import javax.el.ELContext;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Created by Ker on 22.09.2016.
 */
public abstract class ContextMocker extends FacesContext {
    private ContextMocker() {
    }

    private static final Answer RELEASE = invocation -> {
        setCurrentInstance(null);
        return null;
    };

    public static FacesContext mockFacesContext() {
        FacesContext context = Mockito.mock(FacesContext.class);
        setCurrentInstance(context);
        Mockito.doAnswer(RELEASE)
                .when(context)
                .release();
        return context;
    }
}

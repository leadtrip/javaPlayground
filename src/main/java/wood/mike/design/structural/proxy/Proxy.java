package wood.mike.design.structural.proxy;

import java.util.List;

/**
 * Various reasons for proxy
    * Remote proxy e.g. RMI where proxy represents local non-proxy object in another VM
    * Virtual proxy where heavy non-proxy resource is loaded behind scenes and proxy provides something up front while waiting
    * Protection proxy where non-proxy doesn't have access to certain resources but proxy does and fetches for non-proxy
    * Smart proxy where additional layer of protection surrounds non-proxy resource
 */
public class Proxy {
    public static void main(String[] args) {
        ResourceAccess proxy = new AccessAnyResourceProxy();
        proxy.access( "xyz" );
    }
}

// Interface or subject
interface ResourceAccess {
    void access( String resource );
}

// Default implementation or real subject
class AccessAnyResource implements ResourceAccess {
    @Override
    public void access( String resource ) {
        System.out.printf("Getting %s%n", resource );
    }
}

// Proxy wraps object and limits access
class AccessAnyResourceProxy implements ResourceAccess {

    AccessAnyResource def = new AccessAnyResource();

    @Override
    public void access(String resource) {
        if ( canAccess( resource ) ) {
            def.access( resource );
        }
        else {
            throw new UnsupportedOperationException( "Get out" );
        }
    }

    private boolean canAccess(String resource) {
        return List.of("abc", "def", "ghi").contains(resource);
    }
}



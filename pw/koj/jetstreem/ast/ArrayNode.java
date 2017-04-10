package pw.koj.jetstreem.ast;

import java.util.ArrayList;

public class ArrayNode extends ExprNode {
    protected Location location;
    protected String ns;
    protected ArrayList<String> headers;
    protected ArrayList<Node> data;


    public ArrayNode() {}

    public ArrayNode(Location loc) {
        super();
        this.location = loc;
        this.ns = null;
        this.headers = null;
        this.data = new ArrayList<Node>();
    }

    public ArrayNode(Location loc, String ns, ArrayList<String> headers, ArrayList<Node> data) {
        super();
        this.location = loc;
        this.ns = ns;
        this.headers = headers;
        this.data = data;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public String getNs() {
        return ns;
    }

    public void setNs(String ns) {
        this.ns = ns;
    }

    public ArrayList<String> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayList<String> headers) {
        this.headers = headers;
    }

    public ArrayList<Node> getData() {
        return data;
    }

    public void setData(ArrayList<Node> data) {
        this.data = data;
    }

    protected void _dump(Dumper d) {
        // TBD
    }

    public Location location() {
        return location;
    }

    public ArrayNode withHeader() {
        for(int i = 0; i < data.size(); i++) {
          Node d = data.get(i);
          if (d instanceof PairNode) {
            if (headers == null) {
              headers = new ArrayList<String>(data.size());
            }
            PairNode pn = (PairNode)d;
            headers.set(i, pn.key());
            data.set(i, pn.value());
          }
        }
        return this;
    }
        
    public void add(Node n) {
        data.add(n);
    }

    public void prepend(Node n) {
        data.add(0, n);
    }

    public Ir accept(Visitor visitor, Context ctx) {
        visitor.visit(this, ctx);
    }
        
}


abstract class Media implements Comparable<Media>
{
    protected String title;
	public String getTitle()  { return title; }
	public int compareTo(Media a) {
		if(this instanceof DVD) {
			if(a instanceof DVD) {
				DVD d = (DVD) this;
				DVD v = (DVD) a;
				if((d.getTitle()).compareTo(v.getTitle()) > 0) {
					return 1;
				} else if ((d.getTitle()).compareTo(v.getTitle()) == 0) {
					return d.getYear() - v.getYear();
				} else {
					return -1;
				}
			}
			return -1;
		} else if(this instanceof Book) { 
			if(a instanceof Book) {
				Book b = (Book) this;
				Book o = (Book) a;
				if((b.getTitle()).compareTo(o.getTitle()) > 0) {
					return 1;
				} else if((b.getTitle()).compareTo(o.getTitle()) == 0) {
					return (b.getAuthor()).compareTo(o.getAuthor());
				} else {
					return -1;
				}
			}
			return 1;
		} else {
			return 0;
		}
	}
}
public void traverse(Visitor<T> visitor){
	Iterator<T> iter = this.iterator();
	T currData;

	while(iter.hasNext())
	{
		visitor.visit(iter.next()); // VISIT the data
	}

} // end traverse




class MusicVisitor implements Visitor<Music>{
	@Override
	public void visit(Music m){
		System.out.println("Title: " + m.title);
		System.out.println("Composer: " + m.composer);
	}
}



public static void main( String [] args ){
	Music [] musicArray = {
		new Music("Moonlight Sonata",  "Beethoven", 27),
		new Music("Brandenburg Concerto #3", "Bach", 1048),
		new Music("Prelude in e minor", "Chopin", 28) 
	};

	LList<Music>  musicList = new LList<Music>();
	
	for( int i=0; i  < musicArray.length ; ++i ){
		musicList.add(musicArray[i]);
	}
	musicList.traverse(new MusicVisitor());
}
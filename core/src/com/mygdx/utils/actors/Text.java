package com.mygdx.utils.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Align;

public class Text extends AnimatableActor{
	
	private BitmapFont bitFont;
	private GlyphLayout layout;

	private String string;

	private float fontSize;
	
	public Text(float size, String text){
		string = text;
		bitFont = createFonts(bitFont);
		bitFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this.layout = new GlyphLayout(bitFont, text);
		this.setFontSize(size);
		this.fontSize = size;
	}
	
	public void updateLayout(){
		layout.setText(bitFont,string,getColor(),1080,Align.left,false);
		this.setSize(this.layout.width, this.layout.height);
	}
	
	@Override
	public void render(Batch batch, float parentAlpha){
		getColor().a = opacity;
		bitFont.setColor(getColor());
		//batch.setColor(getColor());
		bitFont.draw(batch, string, this.getX(), this.getY());
	}

	@Override
	public void update(float delta){

	}
	
	public static BitmapFont createFonts(BitmapFont font) {
	    FileHandle fontFile = Gdx.files.internal("fonts/Uni Sans Thin.otf");
	    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
	    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	    parameter.size = 100;
	    parameter.genMipMaps = true;
	    font = generator.generateFont(parameter);
	    generator.dispose();
	    return font;
	}
	
	public void setText(String text){
		string = text;
		this.updateLayout();
	}

	public void setTextCentered(String text,float width){
		string = text;
		this.layout.setText(bitFont, string,getColor(),width, Align.center,true);
		this.setSize(this.layout.width, this.layout.height);
	}
	
	public void setFontSize(float size){
		if(size<=0)
			size = 1f;
		bitFont.getData().setScale(.02f*size);
		bitFont.getData().scaleX *= .8f;
		this.fontSize = size;
		updateLayout();
	}

	public String getText(){
		return string;
	}
	
	public float getFontSize(){
		return fontSize;
	}

	@Override
	protected void sizeChanged(){
		updateLayout();
	}

	public void dispose(){
		bitFont.dispose();
	}

}

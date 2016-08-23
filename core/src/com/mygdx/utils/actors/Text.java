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
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Text extends AnimatableActor{
	
	private BitmapFont bitFont;
	private GlyphLayout size;

	String string;

	float fontSize;
	
	public Text(float size, String text){
		string = text;
		bitFont = createFonts(bitFont);
		bitFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this.size = new GlyphLayout(bitFont, text);
		this.setFontSize(size);
		this.fontSize = size;
	}
	
	public void updateSize(){
		this.size.setText(bitFont, string);
		this.setSize(this.size.width, this.size.height);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		bitFont.setColor(getColor().r,getColor().g,getColor().b,opacity);
		bitFont.draw(batch, string, this.getX(), this.getY());
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
		this.updateSize();
	}
	
	public void setFontSize(float size){
		if(size<=0)
			size = 1f;
		bitFont.getData().setScale(.02f*size);
		bitFont.getData().scaleX *= .8f;
		this.fontSize = size;
		updateSize();
	}
	
	public float getFontSize(){
		return fontSize;
	}

	@Override
	protected void sizeChanged(){
		updateSize();
	}

	public void dispose(){
		bitFont.dispose();
	}

}

/**
 * SeeScore Android API
 * Dolphin Computing http://www.dolphin-com.co.uk
 */

package uk.co.dolphin_com.sscore;

/**
 * Define an item with start time and duration in MusicXML divisions
 */
public class TimedItem extends Item
{
	/**
	 * The start time of the item relative to the bar start in divisions
	 */
	public final int start;
	
	/**
	 * The duration of the item (note or rest) in divisions
	 */
	public final int duration;

	/**
	 * @return true if this is a NoteItem or ChordItem which can return a valid NoteItem from baseNoteItem()
	 */
	public boolean hasBaseNote()
	{
		return false;
	}

	/**
	 *
	 * @return a NoteItem representing the base note of the chord, or the note if not a chord
	 */
	public NoteItem baseNoteItem()
	{
		return null;
	}

	public String toString()
	{
		return super.toString() + " start:" + start + ((duration > 0) ? " duration:" + duration : "") + " ";
	}

	TimedItem(int type, int staff, int item_h, int start, int duration) {
		super(type,staff,item_h);
		this.start = start;
		this.duration = duration;
	}
}
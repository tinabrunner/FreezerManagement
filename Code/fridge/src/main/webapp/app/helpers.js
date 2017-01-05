/**
 * Created by phi on 08.12.16.
 */
var empty = function(v) {
	var type = typeof v;
	if(type === 'undefined')
		return true;
	if(type === 'boolean')
		if(v === false)
			return true;
	if(v === null)
		return true;
	if(v == undefined)
		return true;
	if(type === 'array') {
		if (v.length < 1)
			return true;
	}
	else if(type === 'string') {
		if (v.length < 1 )
			return true;
		else if(parseInt(v)===0)
			return true;
	}
	else if(type === 'object') {
		if(Object.keys(v).length < 1)
			return true;
	}
	else if(type === 'number') {
		if(v === 0)
			return true;
	}
	return false;
}
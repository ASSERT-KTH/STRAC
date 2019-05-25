(module 
	
	;;j < words[lengthProperty]
	
	;;Subtree size 5
	
	(func $Gleitsaoostus (param  i32) (param  i32) (result i32) 
		
		;;j
		get_local 0
		
		;;words[lengthProperty]
		get_local 1
		i32.lt_u
		
	)
	(export "Gleitsaoostus" (func $Gleitsaoostus))
)
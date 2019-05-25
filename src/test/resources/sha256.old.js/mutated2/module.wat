(module 
	
	;;i < ascii[lengthProperty]
	
	;;Subtree size 5
	
	(func $Aaaaoaoiiu (param  i32) (param  i32) (result i32) 
		
		;;i
		get_local 0
		
		;;ascii[lengthProperty]
		get_local 1
		i32.lt_u
		
	)
	(export "Aaaaoaoiiu" (func $Aaaaoaoiiu))
)
(module 
	
	;;(hash[4] + temp1)|0
	
	;;Subtree size 7
	
	(func $Qaaoowheeji (param  i32) (param  i32) (result i32) 
		
		;;hash[4]
		get_local 0
		
		;;temp1
		get_local 1
		i32.add
		i32.const 0
		i32.or
		
	)
	(export "Qaaoowheeji" (func $Qaaoowheeji))
)
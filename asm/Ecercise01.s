			TTL CMPE 250 Exercise One
			
;****************************************************************
;This program loads four constants from ROM
;And stores them as variables in RAM. 
;Name:  Justin Peterson
;Date:  08/27/2015
;Class:  CMPE-250
;Section:  CMPE-250-01L3 [TR 2-3:50PM]
;---------------------------------------------------------------
;Keil Simulator Template for KL46
;R. W. Melton
;January 23, 2015
;****************************************************************
;Assembler directives
            THUMB
            OPT    64  ;Turn on listing macro expansions
;****************************************************************
;EQUates
;Vectors
VECTOR_TABLE_SIZE EQU 0x000000C0
VECTOR_SIZE       EQU 4           ;Bytes per vector
;Stack
SSTACK_SIZE EQU  0x00000100
;****************************************************************
;Program
;Linker requires Reset_Handler
            AREA    MyCode,CODE,READONLY
            ENTRY
            EXPORT  Reset_Handler
Reset_Handler
main
;---------------------------------------------------------------
;>>>>> begin main program code <<<<<
;Disable interrupts
			CPSID I
			
MainLoop
			NOP                   ; no-op
			LDR		R2,=ConstData ; ConstPtr = &ConstData
			MOVS	R3,#0x02	  ; Counter = 2 hex value
			
Loop
			LDR		R1, [R2, #0]  ; R1 = ConstPtr[0]
			LDR		R0,=VarData   ; VarPtr = &VarData[0]
			STR		R1,[R0, #0]	  ; VarPtr[0] = ConstPtr[0]
			LDR		R1,[R2, #4]	  ; R1 = ConstPtr[1]
			STR		R1,[R0, #4]	  ; VarPtr[1] = ConstPtr[1]
			ADDS	R2, #8		  ; ConstPtr = &(ConstPtr[2])
			SUBS	R3, #1		  ; Counter--
			BNE		Loop          ; While counter != 0
			NOP					  ; no-op
			B		MainLoop
;>>>>>   end main program code <<<<<
;Stay here
            B       .
;---------------------------------------------------------------
;>>>>> begin subroutine code <<<<<
;>>>>>   end subroutine code <<<<<
            ALIGN
;****************************************************************
;Vector Table Mapped to Address 0 at Reset
;Linker requires __Vectors to be exported
            AREA    RESET, DATA, READONLY
            EXPORT  __Vectors
            EXPORT  __Vectors_End
            EXPORT  __Vectors_Size
__Vectors 
                                      ;ARM core vectors
            DCD    __initial_sp       ;00:end of stack
            DCD    Reset_Handler      ;reset vector
            SPACE  (VECTOR_TABLE_SIZE - (2 * VECTOR_SIZE))
__Vectors_End
__Vectors_Size  EQU     __Vectors_End - __Vectors
            ALIGN
;****************************************************************
;Constants
            AREA    MyConst,DATA,READONLY
;>>>>> begin constants here <<<<<
ConstData	DCD		0x0000000A,0x0000000B,0x00000010,10
;>>>>>   end constants here <<<<<
;****************************************************************
            AREA    |.ARM.__at_0x1FFFE000|,DATA,READWRITE,ALIGN=3
            EXPORT  __initial_sp
;Allocate system stack
            IF      :LNOT::DEF:SSTACK_SIZE
SSTACK_SIZE EQU     0x00000100
            ENDIF
Stack_Mem   SPACE   SSTACK_SIZE
__initial_sp
;****************************************************************
;Variables
            AREA    MyData,DATA,READWRITE
;>>>>> begin variables here <<<<<
VarData		SPACE	8
;>>>>>   end variables here <<<<<
            END
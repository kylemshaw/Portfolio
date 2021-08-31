;TV2U
;Kyle Shaw (200 299 741)
;ENEL 387 Project
;April 8th, 2014

;Directives
	PRESERVE8
	THUMB
	
;**************** Equates *************************************88
INITIAL_MSP EQU	0X20001000 ;Initial main stack pointer

GPIOA_CRL	EQU	0X40010800 ;Config Register (0-7)
GPIOA_CRH	EQU	0X40010804 ;Config Register (8-15)
GPIOA_IDR	EQU	0X40010808 ;Input Data Register
GPIOA_ODR	EQU	0X4001080C ;Output Data Register
GPIOA_BSRR	EQU	0X40010810 ;Bit Set/Reset Register
GPIOA_BRR	EQU	0X40010814 ;Bit Reset Register
GPIOA_LCKR	EQU	0X40010818 ;Configuration Lock Register

GPIOB_CRL	EQU	0X40010C00 ;Config Register (0-7)
GPIOB_CRH	EQU	0X40010C04 ;Config Register (8-15)
GPIOB_IDR	EQU	0X40010C08 ;Input Data Register
GPIOB_ODR	EQU	0X40010C0C ;Output Data Register
GPIOB_BSRR	EQU	0X40010C10 ;Bit Set/Reset Register
GPIOB_BRR	EQU	0X40010C14 ;Bit Reset Register
GPIOB_LCKR	EQU	0X40010C18 ;Configuration Lock Register

GPIOC_CRL	EQU	0X40011000 ;Config Register (0-7)
GPIOC_CRH	EQU	0X40011004 ;Config Register (8-15)
GPIOC_IDR	EQU	0X40011008 ;Input Data Register
GPIOC_ODR	EQU	0X4001100C ;Output Data Register
GPIOC_BSRR	EQU	0X40011010 ;Bit Set/Reset Register
GPIOC_BRR	EQU	0X40011014 ;Bit Reset Register
GPIOC_LCKR	EQU	0X40011018 ;Configuration Lock Register

;TIM1 - Base Addr: 0x40012C00
TIM1_CR1	EQU 0x40012C00 ; TIM1 Control Register 1
TIM1_CR2	EQU 0x40012C04 ; TIM1 Control Register 2
TIM1_SMCR	EQU 0x40012C08 ; TIM1 Slave Mode Control Register
TIM1_DIER	EQU 0x40012C0C ; TIM1 DMA/Interrupt Enable Register
TIM1_SR		EQU 0x40012C10 ; TIM1 Status Register
TIM1_EGR	EQU 0x40012C14 ; TIM1 Event Generation Register
TIM1_CCMR1	EQU 0x40012C18 ; TIM1 Compare/Capture Mode Register 1
TIM1_CCMR2	EQU 0x40012C1C ; TIM1 Compare/Capture Mode Register 2
TIM1_CCER	EQU 0x40012C20 ; TIM1 Compare/Capture Enable Register
TIM1_CNT	EQU 0x40012C24 ; TIM1 Counter
TIM1_PSC	EQU 0x40012C28 ; TIM1 Prescaler
TIM1_ARR	EQU 0x40012C2C ; TIM1 Auto-reload Register
TIM1_RCR	EQU 0x40012C30 ; TIM1 Repetition Counter Register
TIM1_CCR1	EQU 0x40012C34 ; TIM1 Capture/Compare Register 1
TIM1_CCR2	EQU 0x40012C38 ; TIM1 Capture/Compare Register 2
TIM1_CCR3	EQU 0x40012C3C ; TIM1 Capture/Compare Register 3
TIM1_CCR4	EQU 0x40012C40 ; TIM1 Capture/Compare Register 4
TIM1_BDTR	EQU 0x40012C44 ; TIM1 Break and Dead-time Register
TIM1_DCR	EQU 0x40012C48 ; TIM1 DMA Control Register
TIM1_DMAR	EQU 0x40012C4C ; TIM1 DMA Address Register

;TIM15 - Base Addr: 0x40014000
TIM15_CR1	EQU 0x40014000 ; TIM15 Control Register 1
TIM15_CR2	EQU 0x40014004 ; TIM15 Control Register 2
TIM15_SMCR	EQU 0x40014008 ; TIM15 Slave Mode Control Register
TIM15_DIER	EQU 0x4001400C ; TIM15 DMA/Interrupt Enable Register
TIM15_SR	EQU 0x40014010 ; TIM15 Status Register
TIM15_EGR	EQU 0x40014014 ; TIM15 Event Generation Register
TIM15_CCMR1	EQU 0x40014018 ; TIM15 Compare/Capture Mode Register 1
TIM15_CCER	EQU 0x40014020 ; TIM15 Compare/Capture Enable Register
TIM15_CNT	EQU 0x40014024 ; TIM15 Counter
TIM15_PSC	EQU 0x40014028 ; TIM15 Prescaler
TIM15_ARR	EQU 0x4001402C ; TIM15 Auto-reload Register
TIM15_RCR	EQU 0x40014030 ; TIM15 Repetition Counter Register
TIM15_CCR1	EQU 0x40014034 ; TIM15 Capture/Compare Register 1
TIM15_CCR2	EQU 0x40014038 ; TIM15 Capture/Compare Register 2
TIM15_BDTR	EQU 0x40014044 ; TIM15 Break and Dead-time Register
TIM15_DCR	EQU 0x40014048 ; TIM15 DMA Control Register
TIM15_DMAR	EQU 0x4001404C ; TIM15 DMA Address Register

;TIM16 - Base Addr: 0x40014400
TIM16_CR1	EQU 0x40014400 ; TIM16 Control Register 1
TIM16_CR2	EQU 0x40014404 ; TIM16 Control Register 2
TIM16_DIER	EQU 0x4001440C ; TIM16 DMA/Interrupt Enable Register
TIM16_SR	EQU 0x40014410 ; TIM16 Status Register
TIM16_EGR	EQU 0x40014414 ; TIM16 Event Generation Register
TIM16_CCMR1	EQU 0x40014418 ; TIM16 Compare/Capture Mode Register 1
TIM16_CCER	EQU 0x40014420 ; TIM16 Compare/Capture Enable Register
TIM16_CNT	EQU 0x40014424 ; TIM16 Counter
TIM16_PSC	EQU 0x40014428 ; TIM16 Prescaler
TIM16_ARR	EQU 0x4001442C ; TIM16 Auto-reload Register
TIM16_RCR	EQU 0x40014430 ; TIM16 Repetition Counter Register
TIM16_CCR1	EQU 0x40014434 ; TIM16 Capture/Compare Register 1
TIM16_BDTR	EQU 0x40014444 ; TIM16 Break and Dead-time Register
TIM16_DCR	EQU 0x40014448 ; TIM16 DMA Control Register
TIM16_DMAR	EQU 0x4001444C ; TIM16 DMA Address Register

;Registers for config and enabling the clocks
;Use Reset and Clock Control Register (RCC) base address = 0x40021000
RCC_APB2ENR EQU	0X40021018 ;enable/disable peripheral clocks

;Servo Constants
LED_TEST_TIME EQU 8000000 ;3sec
SERVO_DELAY EQU 1   ;max speed of servo is 3ms/1 degree.
SERVO_MOVE EQU 2;1	;if changed also adjust limits
SERVO1_MAX	EQU	231;232	;Max pulse width for servo 1 (2400us = 180 degrees = 240)
SERVO1_MIN	EQU	61;61	;Min pulse width for servo 1 (600us = 0 degrees = 60)
SERVO2_MAX	EQU	239;240	;Max pulse width for servo 2 
SERVO2_MIN	EQU	61;60	;Min pulse width for servo 2 
SERVO3_MAX	EQU	237;238	;Max pulse width for servo 3 
SERVO3_MIN	EQU	61;60	;Min pulse width for servo 3
	
;Input Data Memory Locations
IR_CMD    EQU 0X20000000 ;processed cmd ready for interpretation
CMD_BIT0  EQU 0X22000000 ;start of bit alias address for IR_CMD
GARBAGE1  EQU 0X20000004 ;first bit read in every communication
GARBAGE2  EQU 0X20000008 ;second bit read in every communication (count between signals)
IN_BIT1   EQU 0X2000000C ;start of data to be stored in IR_CMD
IN_BIT2   EQU 0X20000010
IN_BIT3   EQU 0X20000014
IN_BIT4   EQU 0X20000018
IN_BIT5   EQU 0X2000001C
IN_BIT6   EQU 0X20000020
IN_BIT7   EQU 0X20000024
IN_BIT8   EQU 0X20000028
IN_BIT9   EQU 0X2000002C
IN_BIT10  EQU 0X20000030
IN_BIT11  EQU 0X20000034
IN_BIT12  EQU 0X20000038

;Input Signal Constants
;Ideal: ONE = 1800us, ZERO = 1200us
ONE_MAX    EQU 0X3A00;1856us
ONE_MIN    EQU 0X3500;1696us
ZERO_MAX   EQU 0X2900;1312us
ZERO_MIN   EQU 0X2400;1152us

	
;_______________________________________________________________________

;***  Vectors *************************************************************
;Vector Table mapped to address 0 at Reset
			AREA	RESET, Data, READONLY
			EXPORT __Vectors
	
__Vectors
			DCD	INITIAL_MSP		;stack pointer value when stack is empty
			DCD Reset_Handler	;reset vector
			DCD nmi_ISR			; --
			DCD h_fault_ISR		;|
			DCD m_fault_ISR		;| When errors happen go to these labels so you know
			DCD b_fault_ISR 	;|
			DCD u_fault_ISR 	; --
;__________________________________________________________________________________-			
			
			AREA	MYCODE, CODE, READONLY
					EXPORT Reset_Handler
					ENTRY
	
Reset_Handler	PROC

clock_init
	ldr	R6,=RCC_APB2ENR
	ldr R0, =0x0003081D	;Enable Alt Fcn IO, GPIO ABC, TIM1, TIM15, and TIM16
	str	R0,[R6]	
				
gpio_init
	ldr	R6,= GPIOA_CRL
	ldr R0,= 0x4444BB44	; A2 = TIM15 Ch1, A3 = TIM15 Ch2 (PWM) 
	str	R0,[R6]
	
	ldr	R6,= GPIOB_CRH
	ldr R0,= 0x4444444B	;B8 = TIM16 Ch 1 (PWM) 
	str	R0,[R6]
	
	ldr	R6,= GPIOA_CRH
	ldr R0,= 0x44444444	;set PA8 = TIM1 Input Capture (floating input)
	str	R0,[R6]
	
	ldr	R6,= GPIOC_CRL
	ldr R0,= 0x33333333	; C0-7 are push pull outputs for LEDs
	str	R0,[R6]	
	
	LDR R6, = GPIOC_BSRR ;load bit set/reset
	MOV R0, #0x01        ;Make LED off by default (set to 1)
	STR R0, [R6]        
		
	ldr	R6,= GPIOC_CRH
	ldr R0,= 0x44444444	;floating inputs for switches
	str	R0,[R6]
	
tim1_init
	ldr R6, = TIM1_CR1
	mov R0, #0x01 ;set CEN bit high to start up system
	str R0, [R6]
	
	ldr R6, = TIM1_EGR
	mov R0, #0x01 ;dump values from shadow regs to actual regs
	str R0, [R6]
	
	ldr R6, = TIM1_CCMR1
	mov R0, #0x0201 ;set active inputs for ch1 and ch2
	str R0, [R6]
	
	ldr R6, = TIM1_CCER
	mov R0, #0x0031 ;Set active edge and enable counter for ch 1 and ch2
	str R0, [R6]
	
	ldr R6, = TIM1_SMCR
	mov R0, #0x0054 ;select trigger input and and config slave mode controller on reset
	str R0, [R6]
		
	ldr R6, = TIM1_CR1
	mov R0, #0x01 ;set CEN bit high to start up system
	str R0, [R6]
	
tim15_init
	ldr R6, = TIM15_CR1
	mov R0, #0x01 ;set CEN bit high to start up system
	str R0, [R6]
	
	ldr R6, = TIM15_CR2
	mov R0, #0x0500 ;set OIS1 and OSI2 so each period starts high
	str R0, [R6]
	
	ldr R6, = TIM15_EGR
	mov R0, #0x01 ;dump values from shadow regs to actual regs
	str R0, [R6]
	
	ldr R6, = TIM15_CCMR1
	mov R0, #0x6C6C ;set OC1M and OCM2 to 110 and the OCxPE and OCxFE bits for both channels
	str R0, [R6]
	
	ldr R6, = TIM15_CCER
	mov R0, #0x11 ;enable the counter clock
	str R0, [R6]
	
	ldr R6, = TIM15_PSC
	mov R0, #79 ;divide value to get 2.66665MHz clock
	str R0, [R6]
	
	ldr R6, = TIM15_ARR
	mov R0, #2000 ;Period so that it is 20ms
	str R0, [R6]
	
	ldr R6, = TIM15_CCR1
	mov R0, #145 ;Ch1 PA2 PW reset value = 90 degrees 
	str R0, [R6]
	
	ldr R6, = TIM15_CCR2
	mov R0, #145 ;Ch2 PA3 PW reset value = 90 degrees
	str R0, [R6]
	
	ldr R6, = TIM15_BDTR
	mov R0, #0x8400 ;set master enable and OSSI
	str R0, [R6]
	
	ldr R6, = TIM15_CR1
	mov R0, #0x01 ;set CEN bit high to start up system
	str R0, [R6]
	
tim16_init
	ldr R6, = TIM16_CR1
	mov R0, #0x01 ;set CEN bit high to start up system
	str R0, [R6]
	
	ldr R6, = TIM16_CR2
	mov R0, #0x0100 ;set OIS1 bit 8 so each period starts high
	str R0, [R6]
	
	ldr R6, = TIM16_EGR
	mov R0, #0x01 ;dump values from shadow regs to actual regs
	str R0, [R6]
	
	ldr R6, = TIM16_CCMR1
	mov R0, #0x6C ;set OC1M to 110 and PE (bit 3) Fe (bit 2) high
	str R0, [R6]
	
	ldr R6, = TIM16_CCER
	mov R0, #0x01 ;enable the counter clock
	str R0, [R6]
	
	ldr R6, = TIM16_PSC
	mov R0, #79 ;divide value to get 2.66665MHz clock
	str R0, [R6]
	
	ldr R6, = TIM16_ARR
	mov R0, #2000 ;Period so that it is 20ms
	str R0, [R6]
	
	ldr R6, = TIM16_CCR1
	mov R0, #145 ;PB8 PW reset value is 90 degrees
	str R0, [R6]
	
	ldr R6, = TIM16_BDTR
	mov R0, #0x8400 ;set master enable and OSSI
	str R0, [R6]
	
	ldr R6, = TIM16_CR1
	mov R0, #0x01 ;set CEN bit high to start up system
	str R0, [R6]	

	ALIGN
	ENDP		
;**************************************************************************
;Main
;**************************************************************************	
main		PROC
	
	ldr R5, = GARBAGE1 ;initialize address to start of data
	mov R4, #0		   ;initialize bits in counter

check_input
	ldr R6, = TIM1_SR
	ldr R0, [R6]
	AND R0, #0x02
	CMP R0, #0x02       ;check to see if the CC1IF flag has been set
	BNE check_input
	
	ldr R6, = TIM1_CCR1 ;A bit was recieved (CC1IF was set) 
	ldr R0, [R6]
	str R0, [R5] , #0x04 ;store the period into memory and increment memory address
	ADD R4, #1           ;increment bit counter 
	CMP R4, #14          ;full cmd is 2 garbage bits and 12 good bits
	BNE check_input      ;wait for next bit
	
	BL process_signal	;once 12 bits plus 2 garbage bits have been stored process data

process_cmd
	LDR R6, =IR_CMD  ;read cmd from memory and clean up
	LDR R0, [R6]
	LDR R1, =0xFFF
	AND R0, R1
		
	;Check cmds. 
	LDR R1, =0x815	  ;communication test cmd	
	CMP R0, R1	     
	BEQ test_led      ;received received comm test cmd
	
	LDR R1, =0x8B4	  ;load servo1_right cmd constant	
	CMP R0, R1	        
	BEQ servo1_right  ;received servo1_right cmd
	
	LDR R1, =0x8B3    ;load servo1_left cmd constant
	CMP R0, R1	      
	BEQ servo1_left   ;received servo1_left cmd
	
	LDR R1, =0x8B2    ;load servo2_right cmd constant
	CMP R0, R1  
	BEQ servo2_right  ;received servo2_right cmd
	
	LDR R1, =0x8B8    ;load servo2_left cmd constant
	CMP R0, R1
	BEQ servo2_left   ;received servo2_left cmd
	
	LDR R1, =0x8B1    ;load servo3_right cmd constant
	CMP R0, R1
	BEQ servo3_right  ;received servo3_right cmd
	
	LDR R1, =0x8B0    ;load servo3_left cmd constant
	CMP R0, R1
	BEQ servo3_left   ;received servo3_left cmd
	
	B main			  ;if cmd does not have an action nothing happens, look for next cmd		
	
	ALIGN
	ENDP

;**************************************************************************
;test_led
;**************************************************************************				
test_led PROC
	
	LDR R6, = GPIOC_BSRR ;load the output data register address
	MOV R0, #0x00010000       
	STR R0, [R6]        ;turn on the LED
	
	LDR R1, =LED_TEST_TIME
	BL delay			
	
	MOV R0, #0x01
	STR R0, [R6]	;turn off the led
	
	B main
	
	ALIGN
	ENDP

;**************************************************************************
;servo1_right
;**************************************************************************				
servo1_right	PROC

	LDR R6, = TIM15_CCR1  ;load current PW address
	LDR R0, [R6]		 ;load current PW value
	LDR R1, =SERVO1_MAX   ;load the max pulse width
	CMP R0, R1      	 ;cmp max to current
	IT LT
	ADDLT R0, R0, #SERVO_MOVE	 ;if less than increase pulse width
	
	str R0, [R6]         ;set the new PW value 
	
	ldr R6, = TIM15_EGR
	mov R2, #0x01        ;set CEN bit high lock in change
	str R2, [R6]		
	
	;LDR R1, =SERVO_DELAY
	;BL delay
	
	B main
	
	ALIGN
	ENDP

;**************************************************************************
;servo1_left
;**************************************************************************				
servo1_left	PROC
	
	LDR R6, = TIM15_CCR1  ;load current PW address
	LDR R0, [R6]		 ;load current PW value
	LDR R1, =SERVO1_MIN   ;load the max pulse width
	CMP R1, R0      	 ;cmp max to current
	IT LT
	SUBLT R0, R0, #SERVO_MOVE	 ;if greater than min decrease the pulse width
	
	str R0, [R6]         ;set the new PW value
 	
	ldr R6, = TIM15_EGR
	mov R2, #0x01        ;set CEN bit high lock in change
	str R2, [R6]		
	
	;LDR R1, =SERVO_DELAY
	;BL delay
	
	B main
	
	ALIGN
	ENDP
		
;**************************************************************************
;servo2_right
;**************************************************************************				
servo2_right	PROC

	LDR R6, = TIM15_CCR2  ;load current PW address
	LDR R0, [R6]		 ;load current PW value
	LDR R1, =SERVO2_MAX   ;load the max pulse width
	CMP R0, R1      	 ;cmp max to current
	IT LT
	ADDLT R0, R0, #SERVO_MOVE	 ;if less than increase pulse width
	
	str R0, [R6]         ;set the new PW value
 	
	ldr R6, = TIM15_EGR
	mov R2, #0x01        ;set CEN bit high lock in change
	str R2, [R6]		
	
	;LDR R1, =SERVO_DELAY
	;BL delay
	
	B main
	
	ALIGN
	ENDP

;**************************************************************************
;servo2_left
;**************************************************************************				
servo2_left	PROC
	
	LDR R6, = TIM15_CCR2  ;load current PW address
	LDR R0, [R6]		 ;load current PW value
	LDR R1, =SERVO2_MIN   ;load the max pulse width
	CMP R1, R0      	 ;cmp max to current
	IT LT
	SUBLT R0, R0, #SERVO_MOVE	 ;if less than increase pulse width
	
	str R0, [R6]         ;set the new PW value 	
	
	ldr R6, = TIM15_EGR
	mov R2, #0x01        ;set CEN bit high lock in change
	str R2, [R6]		
	
	;LDR R1, =SERVO_DELAY
	;BL delay
	
	B main
	
	ALIGN
	ENDP

;**************************************************************************
;servo3_right
;**************************************************************************				
servo3_right	PROC

	LDR R6, = TIM16_CCR1  ;load current PW address
	LDR R0, [R6]		 ;load current PW value
	LDR R1, =SERVO3_MAX   ;load the max pulse width
	CMP R0, R1      	 ;cmp max to current
	IT LT
	ADDLT R0, R0, #SERVO_MOVE ;if less than increase pulse width
	
	str R0, [R6]         ;set the new PW value 	
	
	ldr R6, = TIM16_EGR
	mov R2, #0x01        ;set CEN bit high lock in change
	str R2, [R6]		
	
	;LDR R1, =SERVO_DELAY
	;BL delay
	
	B main
	
	ALIGN
	ENDP

;**************************************************************************
;servo3_left
;**************************************************************************				
servo3_left	PROC
	
	LDR R6, = TIM16_CCR1  ;load current PW address
	LDR R0, [R6]		 ;load current PW value
	LDR R1, =SERVO3_MIN   ;load the max pulse width
	CMP R1, R0      	 ;cmp max to current
	IT LT
	SUBLT R0, R0, #SERVO_MOVE ;if less than increase pulse width
	
	str R0, [R6]         ;set the new PW value 
	
	ldr R6, = TIM16_EGR
	mov R2, #0x01        ;set CEN bit high lock in change
	str R2, [R6]		
	
	;LDR R1, =SERVO_DELAY
	;BL delay
	
	B main
	
	ALIGN
	ENDP
		
;**************************************************************************
;process_signal
;**************************************************************************				
process_signal PROC
	
	LDR R5, =IN_BIT1  ;load the start address cmd input data
	LDR R6, =CMD_BIT0 ;load the address to the first bit in cmd
proc_loop
	LDR R0, [R5], #0x04	;load first bit to R0 and increment address
	
	LDR R1, =ONE_MIN    ;load min allowed value for a "1"
	CMP R0, R1
	BLT zero            ;less than "1" pulse, check zero
	
	LDR R1, =ONE_MAX    ;load max allowed value for a "1"
	CMP R0, R1
	BGT invalid_signal	;high pulse is too long 
	
	MOV R0, #1			;within one limits so store a 1
	STR R0, [R6], #0x04 ;write a "1" to cmd bit
	B bound_check       ;move on to next loop iteration
	
zero	
	LDR R1, =ZERO_MAX   ;load max allowed value for a "0"
	CMP R0, R1		 	
	BGT invalid_signal  ;too long for 0 too short for 1
	
	LDR R1, =ZERO_MIN   ;load max allowed value for a "0"
	CMP R0, R1		 	
	BLT invalid_signal  ;not long enough high time
	
	MOV R0, #0			;within "0" limits so store a 0
	STR R0, [R6], #0x04	;write a "0" to cmd bit

bound_check
	AND R0, R5, #0xFF 
	CMP R0, #0x3C     ;past 12th cmd bit?
	IT NE
	BNE proc_loop	  ;no, get the other bits	
	BX LR;            ;yes, go back to main to execute cmd

invalid_signal
	B main  ;stop processing 
	
	ALIGN
	ENDP

;**************************************************************************
;Delay function
;**************************************************************************				
delay		PROC
				
	;Subtract from Delay time until zero
	subs	R1,#1
	bne	delay		;break if zero flag has not been thrown
	bx	LR			;go to location that called delay label
	
	ALIGN
	ENDP

;******** Error Handlers ***************************************
		AREA	HANDLERS, CODE, READONLY
	;Default Handlers for NMI and faults. useful for debugging
nmi_ISR
	b	.
h_fault_ISR
	b	.
m_fault_ISR
	b	.
b_fault_ISR
	b	.
u_fault_ISR
	b	.
	
	END
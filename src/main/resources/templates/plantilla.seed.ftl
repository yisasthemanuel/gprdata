Welcome to the GPR Cup Edition [b]${edition_no}[/b]

This is the seeding according to the first two races standings:

[table]
[trow]
	[th]Spot[/th]
	[th]Manager[/th]
	[th]R1[/th]
	[th]R2[/th]
	[th]AVG[/th]
[/trow]
<#list stats as stat>
[trow]
	[td]${stat?counter}[/td]
	[td]${stat.first_name}[/td]
	[td]${stat.r1}[/td]
	[td]${stat.r2}[/td]
	[td]${stat.r3?string["0.##"]}[/td]
[/trow]
	<#if stat?counter == participants>
[trow]
	[td]--[/td]
	[td]--[/td]
	[td]--[/td]
	[td]--[/td]
	[td]--[/td]
[/trow]
	</#if>
</#list>
[/table]
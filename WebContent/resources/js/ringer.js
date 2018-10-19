var ringer = {
  //countdown_to: "10/31/2014",
  
  rings: {
    
    'H': {
      s: 3600000, // mseconds per hour,
      max: 24
    },
    'M': {
      s: 60000, // mseconds per minute
      max: 60
    },
    'S': {
      s: 1000,
      max: 60
    }
   },
  r_count: 3,
  r_spacing: 15, // px
  r_size: 60, // px
  r_thickness: 5, // px
  update_interval: 11, // ms
    
    
  init: function(time){
	  
	
    $r = ringer;
    $r.countdown_to = time;
    $r.cvs = document.createElement('canvas');
    
    $r.size = { 
      w: ($r.r_size + $r.r_thickness) * $r.r_count + ($r.r_spacing*($r.r_count-1)), 
      h: ($r.r_size + $r.r_thickness) 
    };
    


    $r.cvs.setAttribute('width',$r.size.w);           
    $r.cvs.setAttribute('height',$r.size.h);
    $r.ctx = $r.cvs.getContext('2d');
    $("#ringer").prepend($r.cvs);
    $r.cvs = $($r.cvs);    
    $r.ctx.textAlign = 'center';
    $r.actual_size = $r.r_size + $r.r_thickness;
    $r.countdown_to_time = $r.countdown_to;
    $r.cvs.css({ width: $r.size.w+"px", height: $r.size.h+"px", margin: "0 auto" });
    $r.go();
  },
  ctx: null,
  go: function(){
    var idx=0;
    
    $r.time = (new Date().getTime()) - $r.countdown_to_time;
    
    
    for(var r_key in $r.rings) $r.unit(idx++,r_key,$r.rings[r_key]);      
    
    setTimeout($r.go,$r.update_interval);
  },
  unit: function(idx,label,ring) {
    var x,y, value, ring_secs = ring.s;
    value = parseFloat($r.time/ring_secs);
    $r.time-=Math.round(parseInt(value)) * ring_secs;
    value = Math.abs(value);
    
    x = ($r.r_size*.5 + $r.r_thickness*.5);
    x +=+(idx*($r.r_size+$r.r_spacing+$r.r_thickness));
    y = $r.r_size*.5;
    y += $r.r_thickness*.5;

    
    // calculate arc end angle
    var degrees = 360-(value / ring.max) * 360.0;
    var endAngle = degrees * (Math.PI / 180);
    
    $r.ctx.save();

    $r.ctx.translate(x,y);
    $r.ctx.clearRect($r.actual_size*-0.5,$r.actual_size*-0.5,$r.actual_size,$r.actual_size);

    // first circle
    $r.ctx.strokeStyle = "rgb(225, 225, 225)";
    $r.ctx.beginPath();
    $r.ctx.arc(0,0,$r.r_size/2,0,2 * Math.PI, 2);
    $r.ctx.lineWidth =$r.r_thickness;
    $r.ctx.stroke();
   
    // second circle
    $r.ctx.strokeStyle = "rgb(75, 45, 162)";
    $r.ctx.beginPath();
    $r.ctx.arc(0,0,$r.r_size/2,0,endAngle, 1);
    $r.ctx.lineWidth =$r.r_thickness;
    $r.ctx.stroke();
    
    // label
    $r.ctx.fillStyle = "#000000";
   
    $r.ctx.font = '9px Helvetica';
    $r.ctx.fillText(label, 16, 7);
    $r.ctx.fillText(label, 16, 7);   
    
    $r.ctx.font = '20px Helvetica';
    $r.ctx.fillText(Math.floor(value), 0, 7);
    
    $r.ctx.restore();
  }
}


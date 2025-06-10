fish2 = flip(rot45(fish))
fish3 = rot(rot(fish2))

t = over(fish, over(fish2, fish3))
u = over(over(fish2, rot(fish2)),
         over(rot(rot(fish2)), rot(rot(rot(fish2))))

quartet(p,q,r,s) = above(beside(p,q), beside(r,s))

cycle(p) = quartet(p, rot(p),
                   rot(rot(p)), rot(rot(rot(p))))

side1 = quartet(blank, blank, rot(t), t)
side2 = quartet(side1, side1, rot(t), t)

side[0] = blank
side[n] = quartet(side[n-1], side[n-1], rot(t), t)
o con un lazy language
side = quartet(side, side, rot(t), t)

corner1 = quartet(blank, blank, blank, u)
corter2 = quartet(corner1, side1, rot(side1), u)

corner[0] = blank
corner[n] = quartet(corner[n-1], side, rot(side), u)
o con un lazy language
corner = quartet(corner, side, rot(side), u)

nonet(p, q, r,
      s, t, u,
      v, w, x) = above(1,2,beside(1,2,p,beside(1,1,q,r)),
                 above(1,1,beside(1,2,s,beside(1,1,t,u)),
                 beside(1,2,v,beside(1,1,w,x))))

squarelimit2 = nonet(corner2, side2, rot(rot(rot(corner2))),
                     rot(side2),u,rot(rot(rot(side2))),
                     rot(corner2),rot(rot(side2)),rot(rot(corner2)))
general description
squarelimit = nonet(corner, side, rot(rot(rot(corner))),
                    rot(side),u,rot(rot(rot(side))),
                    rot(corner),rot(rot(side)),rot(rot(corner)))

blank(a,b,c) = {}

beside(p,q)(a,b,c) = p(a,b/2,c) U q(a+b/2,b/2,c) 

above(p,q)(a,b,c) = p(a,b,c/2) U q(a+c/2,b,c/2) 
rot(p)(a,b,c) = p(a+b,c,-b)
flip(p)(a,b,c) = p(a+b,-b,c) 
rot45(p)(a,b,c) = p(a+(b+c)/2,(b+c)/2,(c-b)/2)

beside(m,n,p,q)(a,b,c) = p(a,b*m/(m+n),c) U 
                         q(a+b*m/(m+n),b*n/(m+n),c) 

above(m,n,p,q)(a,b,c) = p(a+c*n/(m+n),b,c*m/(m+n)) U 
                        q(a,b,c*n/(m+n))



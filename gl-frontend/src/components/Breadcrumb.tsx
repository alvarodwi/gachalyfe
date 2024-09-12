import { Link, useLocation } from 'react-router-dom'
import { BreadcrumbLink } from '../models/ui/BreadcrumbLink'
import cx from 'classix'
import { useEffect, useState } from 'react'
import { capitalize } from 'lodash'

interface Props {
  className?: string
}

export default function Breadcrumb(props: Props) {
  const [links, setLinks] = useState<BreadcrumbLink[]>([])
  const location = useLocation()

  useEffect(() => {
    const pathnames = location.pathname.split('/').filter((x) => x)
    const crumbs: BreadcrumbLink[] = [{ to: '/', name: 'Home' }]
    let currentPath = ''
    pathnames.forEach((p) => {
      currentPath += `/${p}`
      crumbs.push({ to: currentPath, name: capitalize(p) })
    })
    setLinks(crumbs)
  }, [location])

  return (
    <div data-id="breadcrumb">
      <span className={cx(props.className, 'flex flex-row')}>
        {links.map((link, i) => (
          <span className="text-sm" key={i}>
            {i === links.length - 1 ? (
              <span> {link.name}</span>
            ) : (
              <div className="flex flex-row items-center">
                <Link
                  to={link.to ?? ''}
                  className="text-black no-underline"
                  key={i}
                >
                  <span className="cursor-pointer underline">{link.name}</span>
                </Link>
                <span className="mx-2"> / </span>
              </div>
            )}
          </span>
        ))}
      </span>
    </div>
  )
}

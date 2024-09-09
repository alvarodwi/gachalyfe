import { Link } from 'react-router-dom'
import { BreadcrumbLink } from '../models/ui/BreadcrumbLink'
import cx from 'classix'

export default function Breadcrumb({
  crumbs,
  className,
}: {
  crumbs: BreadcrumbLink[]
  className?: string
}) {
  return (
    <>
      <div data-id="breadcrumb">
        <span className={cx(className, 'flex flex-row')}>
          {crumbs.map((crumb, i) => (
            <span className="text-sm" key={i}>
              {i === crumbs.length - 1 ? (
                <span> {crumb.title}</span>
              ) : (
                <>
                  <div className="flex flex-row items-center">
                    <Link
                      to={crumb.to ?? ''}
                      className="text-black no-underline"
                      key={i}
                    >
                      <span className="text-blue cursor-pointer underline">
                        {crumb.title}
                      </span>
                    </Link>
                    <div className="i-tabler-chevron-right text-xl" />
                  </div>
                </>
              )}
            </span>
          ))}
        </span>
      </div>
    </>
  )
}

import clsx from 'clsx';
import Heading from '@theme/Heading';
import styles from './styles.module.css';
import Link from '@docusaurus/Link';

const DocumentationList = [
  {
    title: (
      <>
        CraftTweaker <span class="badge badge--primary">Recommended</span>
      </>
    ),
    link: '/docs/crafttweaker',
    description: (
      <>
        Use the power of CraftTweaker and ZenScript to take full control of recipes. 
        MrCrayfish's Furntiure Mod: Refurbished comes with full CraftTweaker integration 
        into all custom recipe types, allowing you to easily add, modify, and remove recipes.
      </>
    ),
  },
  {
    title: 'Datapack',
    link: '/docs/datapack',
    description: (
      <>
        The vanilla method of adding and modifying recipes. Allows you to quickly get started 
        adding and modifying recipes in MrCrayfish's Furntiure Mod: Refurbished. This is a 
        good method if you're only looking to make a few additions/changes.
      </>
    ),
  },
];

function Documentation({title, description, link}) {
  return (
    <div className={clsx('col col--6')}>
      <div class="card-demo">
        <div class="card shadow--lw">
          <div class="card__header">
            <h3>{title}</h3>
            <p>{description}</p>
          </div>
          <div class="card__footer">
            <Link
              className="button button--secondary button--lg button--block"
              to={link}>
              View
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default function HomepageFeatures() {
  return (
    <section className={styles.features}>
      <div className="container">
        <div className="row">
          {DocumentationList.map((props, idx) => (
            <Documentation key={idx} {...props} />
          ))}
        </div>
      </div>
    </section>
  );
}
